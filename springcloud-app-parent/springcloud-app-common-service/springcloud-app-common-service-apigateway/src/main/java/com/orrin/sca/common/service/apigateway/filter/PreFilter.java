package com.orrin.sca.common.service.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

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

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		Object bestMatchingPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

		log.info("[*]bestMatchingPattern = {}", bestMatchingPattern == null ? "null" : bestMatchingPattern.toString());

		log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
		Object accessToken = request.getHeader("token");

		log.info(" accessToken = {}",accessToken);

		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String thisname = headerNames.nextElement();
			String thisvalue = request.getHeader(thisname);
			System.out.println("header name = "+thisname + "	, value = "+ thisvalue);
		}

		Enumeration<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String thisname = parameterNames.nextElement();
			String thisvalue = request.getParameter(thisname);
			System.out.println("parameterNames name = "+thisname + "	, value = "+ thisvalue);
		}

		Enumeration<String> attributeNames = request.getAttributeNames();
		while(attributeNames.hasMoreElements()){
			String thisname = attributeNames.nextElement();
			Object thisvalue = request.getAttribute(thisname);
			System.out.println("Attribute name = "+thisname + "	, value = "+ thisvalue.toString());
		}

		if (accessToken == null) {
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
		}
		log.info("ok");
		return null;
	}
}