package com.orrin.sca.common.service.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.orrin.sca.component.jwt.CoverAccessTokenModel;
import com.orrin.sca.component.utils.json.JacksonUtils;
import com.orrin.sca.framework.core.model.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
		String accessToken = request.getHeader("token");

		log.info(" accessToken = {}",accessToken);

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
			CoverAccessTokenModel coverAccessTokenModel = JacksonUtils.decode(jwt.getClaims(), CoverAccessTokenModel.class);

			Date expDate = new Date(coverAccessTokenModel.getExp());

			if(new Date().after(expDate)){
				responseResult = new ResponseResult<>();
				responseResult.setResponseCode("10001");
				responseResult.setResponseMsg("token is expired !");
			}

			if(responseResult != null) {
				ctx.setSendZuulResponse(false);
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
}