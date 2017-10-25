package com.orrin.sca.common.service.uaa.core.secure;

import com.orrin.sca.common.service.uaa.dao.SysResourcesRepository;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilege;
import com.orrin.sca.component.redis.config.Prefixes;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Orrin on 2017/7/12.
 */
public class URLFilterInvocationSecurityMetadataSource  implements FilterInvocationSecurityMetadataSource,InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(URLFilterInvocationSecurityMetadataSource.class);

	private final static List<ConfigAttribute> NULL_CONFIG_ATTRIBUTE = Collections.emptyList();

	//权限集合
	//private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

	@Autowired
	private SysResourcesRepository sysResourcesRepository;

	@Resource(name= "redisTemplate")
	private HashOperations<String,String,RequestAuthForMatcher> opsForHash;

	@Autowired
	private RedissonClient redissonClient;


	private List<HandlerMapping> handlerMappings;

	@Override
	public void afterPropertiesSet() throws Exception {
		//this.requestMap = this.bindRequestMap();
		this.loadResuorce();
		logger.info("资源权限列表 init finished");
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		long startTime = System.currentTimeMillis();

		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		Collection<ConfigAttribute> attrs = NULL_CONFIG_ATTRIBUTE;
		List<RequestAuthForMatcher> requestMatchers = new ArrayList<>();

		Map<String, RequestAuthForMatcher> resourceAndAuth = opsForHash.entries(Prefixes.RESOURCE_AND_AUTHORITIES_SET.getValue());
		if(resourceAndAuth == null || resourceAndAuth.size() == 0) {
			this.loadResuorce();
			resourceAndAuth = opsForHash.entries(Prefixes.RESOURCE_AND_AUTHORITIES_SET.getValue());
		}

		Set<String> reallyRequestPathSet = new HashSet<>();

		if(resourceAndAuth != null){

			/*for(Map.Entry<String, RequestAuthForMatcher> entry : resourceAndAuth.entrySet() ) {
				Collection<ConfigAttribute> atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue().getAuthorityMarks());
				RequestMatcher requestMatcher = new AntPathRequestMatcher(entry.getValue().getResourcePath(),entry.getValue().getRequestMethod().toUpperCase(),true);
				if (requestMatcher.matches(request)) {
					attrs.addAll(atts);
				}
			}*/

			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
			Map<String, HandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(webApplicationContext, HandlerMapping.class, true, false);
			this.handlerMappings = new ArrayList(matchingBeans.values());

			HandlerExecutionChain mappedHandler = null;
			try {
				mappedHandler = getHandler(request);
				if(mappedHandler.getHandler() instanceof HandlerMethod) {
					HandlerMethod handlerMethod = (HandlerMethod)mappedHandler.getHandler();

					RequestMapping classRequestMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);

					ResourcePrivilege methodResourcePrivilege = handlerMethod.getMethodAnnotation(ResourcePrivilege.class);
					RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
					String[] methodPaths = null;
					String[] classPaths = (classRequestMapping.path().length == 0)? classRequestMapping.value() : classRequestMapping.path();

					if(methodResourcePrivilege != null){
						RequestAuthForMatcher requestAuthForMatcher = opsForHash.get(Prefixes.RESOURCE_AND_AUTHORITIES_SET.getValue(),methodResourcePrivilege.resourceGlobalUniqueId());
						attrs = SecurityConfig.createListFromCommaDelimitedString(requestAuthForMatcher.getAuthorityMarks());

						logger.info("URL资源match："+request.getRequestURI()+ " -> " + attrs);
						logger.info("URL资源match execute TimeMillis = {} ", (System.currentTimeMillis() - startTime));
						return attrs;
					}else if(methodRequestMapping!=null) {
						methodPaths = methodRequestMapping.path().length == 0 ? methodRequestMapping.value() : methodRequestMapping.path();
						if( methodPaths != null){
							for(String methodPath : methodPaths){
								String wholePath = methodPath.startsWith("/") ? methodPath : ("/" +methodPath);
								if(classPaths != null){
									for(String classPath : classPaths) {
										wholePath = (classPath.endsWith("/") ? classPath.substring(0,classPath.length()-1) : classPath) + wholePath;
										logger.info("wholePath = {}", wholePath);
										reallyRequestPathSet.add(wholePath);
									}
								}else {
									logger.info("wholePath = {}", wholePath);
									reallyRequestPathSet.add(wholePath);
								}
							}
						}
					}
				}
				for(Map.Entry<String, RequestAuthForMatcher> entry : resourceAndAuth.entrySet() ) {
					//Collection<ConfigAttribute> atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue().getAuthorityMarks());
					RequestMatcher requestMatcher = new AntPathRequestMatcher(entry.getValue().getResourcePath(),entry.getValue().getRequestMethod().toUpperCase(),true);
					if (requestMatcher.matches(request)) {
						requestMatchers.add(entry.getValue());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(requestMatchers != null && requestMatchers.size() == 1){
			attrs = SecurityConfig.createListFromCommaDelimitedString(requestMatchers.get(0).getAuthorityMarks());
		}else if(requestMatchers != null && requestMatchers.size() > 0 && reallyRequestPathSet.size() > 0){
			for(String reallyRequestPath : reallyRequestPathSet) {
				for(RequestAuthForMatcher rafm : requestMatchers) {
					if(rafm.getResourcePath().equalsIgnoreCase(reallyRequestPath)) {
						attrs = SecurityConfig.createListFromCommaDelimitedString(rafm.getAuthorityMarks());
						break;
					}
				}
				if(attrs != null && attrs.size() > 0){
					break;
				}
			}
		}

		logger.info("URL资源match："+request.getRequestURI()+ " -> " + attrs);
		logger.info("URL资源match execute TimeMillis = {} ", (System.currentTimeMillis() - startTime));
		return attrs;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		Map<String, RequestAuthForMatcher> resourceAndAuth = opsForHash.entries(Prefixes.RESOURCE_AND_AUTHORITIES_SET.getValue());
		if(resourceAndAuth == null && resourceAndAuth.size() == 0) {
			this.loadResuorce();
			resourceAndAuth = opsForHash.entries(Prefixes.RESOURCE_AND_AUTHORITIES_SET.getValue());
		}

		if(resourceAndAuth != null){
			for(Map.Entry<String, RequestAuthForMatcher> entry : resourceAndAuth.entrySet() ) {
				Collection<ConfigAttribute> atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue().getAuthorityMarks());
				allAttributes.addAll(atts);

			}
		}

		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return FilterInvocation.class.isAssignableFrom(aClass);
	}

	public void refreshResuorceMap(){
		this.loadResuorce();
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
					map.put("globalUniqueId", (String)o[3]);
					map.put("requestMethod", (String)o[4]);
					list.add(map);
				}
			}else {
				map.put("resourcePath", resourcePaths);
				map.put("authorityMark", (String)o[1]);
				map.put("globalUniqueId", (String)o[3]);
				map.put("requestMethod", (String)o[4]);
				list.add(map);
			}

		}

		return list;
	}

	private Map<String,RequestAuthForMatcher> loadResuorce(){

		Map<String,RequestAuthForMatcher> map = new LinkedHashMap<>();

		List<Map<String,String>> list = getURLResourceMapping();
		Iterator<Map<String,String>> it = list.iterator();
		while(it.hasNext()){
			Map<String,String> rs = it.next();
			String resourcePath = rs.get("resourcePath");
			String authorityMark = rs.get("authorityMark");
			String globalUniqueId = rs.get("globalUniqueId");
			String requestMethod = rs.get("requestMethod");

			resourcePath = resourcePath.startsWith("/") ? resourcePath : ("/" +resourcePath);

			if(map.containsKey(globalUniqueId)){
				RequestAuthForMatcher mark = map.get(resourcePath);
				mark.setAuthorityMarks(mark.getAuthorityMarks()+","+authorityMark);
				map.put(globalUniqueId, mark);
			}else{
				RequestAuthForMatcher mark = new RequestAuthForMatcher();
				mark.setAuthorityMarks(authorityMark);
				mark.setGlobalUniqueId(globalUniqueId);
				mark.setRequestMethod(requestMethod);
				mark.setResourcePath(resourcePath);
				map.put(globalUniqueId, mark);
			}
		}

		RLock lock = redissonClient.getLock(Prefixes.RESOURCE_AND_AUTHORITIES_LOCK.getValue());
		try {
			boolean res = lock.tryLock(10, TimeUnit.SECONDS);
			if(res) {
				opsForHash.putAll(Prefixes.RESOURCE_AND_AUTHORITIES_SET.getValue(), map);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}

		return map;
	}

	/*
	protected Map<RequestMatcher, Collection<ConfigAttribute>> bindRequestMap(){
		Map<RequestMatcher, Collection<ConfigAttribute>> map =
				new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

		Map<String,String> resMap = this.loadResuorce();
		for(Map.Entry<String,String> entry:resMap.entrySet()){
			String key = entry.getKey();
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue());
			map.put(new AntPathRequestMatcher(key), atts);
		}
		return map;
	}
	*/

	protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		Iterator var2 = this.handlerMappings.iterator();

		HandlerExecutionChain handler;
		do {
			if (!var2.hasNext()) {
				return null;
			}

			HandlerMapping hm = (HandlerMapping)var2.next();
			if (logger.isTraceEnabled()) {
				logger.trace("Testing handler map [" + hm + "] in DispatcherServlet with name '" + this.getClass().getName() + "'");
			}

			handler = hm.getHandler(request);
		} while(handler == null);

		return handler;
	}

}
