package com.orrin.sca.common.service.apigateway.server.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.orrin.sca.component.jwt.CoverAccessTokenModel;
import com.orrin.sca.component.privilege.intercept.URLFilterInvocationAuthority;
import com.orrin.sca.component.utils.json.JacksonUtils;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.http.MediaType;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author orrin.zhang on 2017/7/28.
 */
public class PreFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(PreFilter.class);

	/**
	 * pre：可以在请求被路由之前调用
	 * route：在路由请求时候被调用
	 * error：处理请求时发生错误时被调用
	 * post：在route和error过滤器之后被调用
	 * @return
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Autowired
	private DiscoveryClient discovery;

	@Autowired
	private DiscoveryClientRouteLocator routeLocator;

	@Autowired
	private URLFilterInvocationAuthority urlFilterInvocationAuthority;

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		Object bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		log.info("[*]bestMatchingPattern = {}", bestMatchingPattern == null ? "null" : bestMatchingPattern.toString());

		log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
		String accessToken = request.getHeader("Authorization");

		log.info(" accessToken = {}",accessToken);
		String AuthorizationPrefix = accessToken.substring(0, 7).toLowerCase();
		if(AuthorizationPrefix.equalsIgnoreCase("Bearer ")) {
			accessToken = accessToken.substring(7);
		}

		ResponseResult<?> responseResult = null;

		if (!StringUtils.hasText(accessToken)) {
			log.warn("token is empty");
			/*
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			try {
				ctx.getResponse().getWriter().write("token is empty");
			} catch (Exception e) {

			}
			*/
			return null;
		}else {

			Jwt jwt = JwtHelper.decode(accessToken);

			responseResult = determineAccess(jwt);

			if(responseResult != null && !responseResult.getResponseCode().equalsIgnoreCase("00000")) {
				ctx.setSendZuulResponse(false);
				ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				ctx.setResponseStatusCode(401);
				ctx.setResponseBody(JacksonUtils.encode(responseResult));
				ctx.setResponseGZipped(true);
				/*try {
					ctx.getResponse().getWriter().write(JacksonUtils.encode(responseResult));
				} catch (Exception e) {

				}*/
				return null;
			}

		}
		log.info("ok");
		return null;
	}

	protected ResponseResult<?> determineAccess(Jwt jwt) {
		CoverAccessTokenModel coverAccessTokenModel = JacksonUtils.decode(jwt.getClaims(), CoverAccessTokenModel.class);

		ResponseResult<?> responseResult = new ResponseResult<>();
		responseResult.setResponseCode("00000");

		long nowTime = System.currentTimeMillis() / 1000;

		if(nowTime > coverAccessTokenModel.getExp()){
			responseResult = new ResponseResult<>();
			responseResult.setResponseCode("10001");
			responseResult.setResponseMsg("token is expired !");
		}

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		Route route = routeLocator.getMatchingRoute(request.getRequestURI());

		Collection<ConfigAttribute> Attributes = urlFilterInvocationAuthority.getAttributes(route.getLocation(), request);

		return responseResult;
	}
}