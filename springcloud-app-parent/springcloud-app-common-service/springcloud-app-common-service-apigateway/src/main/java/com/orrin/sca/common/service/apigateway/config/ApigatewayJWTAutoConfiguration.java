package com.orrin.sca.common.service.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(ApigatewayJWTProperties.class)
@ConditionalOnProperty(prefix = ApigatewayJWTProperties.PREFIX, value = "keyValue")
public class ApigatewayJWTAutoConfiguration {

    @Autowired
    private ApigatewayJWTProperties apigatewayJWTProperties;

    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(apigatewayJWTProperties.getKeyValue());

        if (StringUtils.hasText(apigatewayJWTProperties.getKeyValue()) && !apigatewayJWTProperties.getKeyValue().startsWith("-----BEGIN")) {
            converter.setSigningKey(apigatewayJWTProperties.getKeyValue());
        }

        if (apigatewayJWTProperties.getKeyValue() != null) {
            converter.setVerifierKey(apigatewayJWTProperties.getKeyValue());
        }

        try {
            converter.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return converter;
    }

    @Bean
    JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
}
