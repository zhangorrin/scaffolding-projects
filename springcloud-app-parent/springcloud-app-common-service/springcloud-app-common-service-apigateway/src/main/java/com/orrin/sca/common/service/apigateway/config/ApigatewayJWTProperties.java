package com.orrin.sca.common.service.apigateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author orrin.zhang on 2017/8/11.
 */
@ConfigurationProperties(ApigatewayJWTProperties.PREFIX)
public class ApigatewayJWTProperties {
	public static final String PREFIX = "apigateway.jwt";

	private String keyValue;

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
}
