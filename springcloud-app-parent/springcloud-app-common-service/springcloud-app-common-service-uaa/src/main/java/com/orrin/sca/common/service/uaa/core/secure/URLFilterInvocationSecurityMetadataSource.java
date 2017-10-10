package com.orrin.sca.common.service.uaa.core.secure;

import com.orrin.sca.common.service.uaa.dao.SysResourcesRepository;
import com.orrin.sca.component.redis.config.Prefixes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Orrin on 2017/7/12.
 */
public class URLFilterInvocationSecurityMetadataSource  implements FilterInvocationSecurityMetadataSource,InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(URLFilterInvocationSecurityMetadataSource.class);

	private final static List<ConfigAttribute> NULL_CONFIG_ATTRIBUTE = Collections.emptyList();

	//权限集合
	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

	@Autowired
	private SysResourcesRepository sysResourcesRepository;

	@Resource(name= "redisTemplate")
	private HashOperations<String, String, Collection<ConfigAttribute>> hashOperations;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.requestMap = this.bindRequestMap();
		logger.info("资源权限列表"+this.requestMap);
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();

		Collection<ConfigAttribute> attrs = NULL_CONFIG_ATTRIBUTE;

		Map<String, Collection<ConfigAttribute>> resourceAndAuth = hashOperations.entries(Prefixes.RESOURCE_AND_AUTHORITIES_SET.getValue());

		if(resourceAndAuth == null){
			logger.info("reflash 资源权限列表 ");
			this.bindRequestMap();
		}

		for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceAndAuth.entrySet()) {

			AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(entry.getKey());

			if (antPathRequestMatcher.matches(request)) {
				attrs =  entry.getValue();
				break;
			}
		}
		logger.info("URL资源："+request.getRequestURI()+ " -> " + attrs);
		return attrs;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return FilterInvocation.class.isAssignableFrom(aClass);
	}

	public void refreshResuorceMap(){
		this.requestMap = this.bindRequestMap();
	}


	/**
	 * 获取到url地址和AUTH_**这种权限标识，注意：不是权限ID和资源ID
	 * @return
	 */
	private List<Map<String,String>> getURLResourceMapping(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();

		List<Object[]> result  = sysResourcesRepository.findAuthResources();
		Iterator<Object[]> it = result.iterator();

		while(it.hasNext()){
			Object[] o = it.next();
			Map<String,String> map = new HashMap<String,String>();
			String resourcePaths = (String)o[0];
			if(resourcePaths.contains(",")){
				String resourcePath[] = resourcePaths.split(",");
				for(String rp : resourcePath) {
					map.put("resourcePath", rp);
					map.put("authorityMark", (String)o[1]);
					list.add(map);
				}
			}else {
				map.put("resourcePath", resourcePaths);
				map.put("authorityMark", (String)o[1]);
				list.add(map);
			}

		}

		return list;
	}

	private Map<String,String> loadResuorce(){
		Map<String,String> map = new LinkedHashMap<String,String>();

		List<Map<String,String>> list = getURLResourceMapping();
		Iterator<Map<String,String>> it = list.iterator();
		while(it.hasNext()){
			Map<String,String> rs = it.next();
			String resourcePath = rs.get("resourcePath");
			String authorityMark = rs.get("authorityMark");

			if(map.containsKey(resourcePath)){
				String mark = map.get("resourcePath");
				map.put(resourcePath, mark+","+authorityMark);
			}else{
				map.put(resourcePath, authorityMark);
			}
		}
		return map;
	}

	protected Map<RequestMatcher, Collection<ConfigAttribute>> bindRequestMap(){
		Map<RequestMatcher, Collection<ConfigAttribute>> map =
				new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

		Map<String,String> resMap = this.loadResuorce();
		for(Map.Entry<String,String> entry:resMap.entrySet()){
			String key = entry.getKey();
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue());
			map.put(new AntPathRequestMatcher(key), atts);
			hashOperations.putIfAbsent(Prefixes.RESOURCE_AND_AUTHORITIES_SET.getValue(),key,atts);
		}

		return map;
	}

}
