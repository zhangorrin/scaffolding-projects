package com.orrin.sca.common.service.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author orrin.zhang on 2017/8/11.
 */
@Service("apigatewayJWTUtils")
public class ApigatewayJWTUtils {

	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired
	private JwtTokenStore jwtTokenStore;

	private OAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException, InvalidTokenException {
		OAuth2AccessToken accessToken = this.jwtTokenStore.readAccessToken(accessTokenValue);
		if (accessToken == null) {
			throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
		} else if (accessToken.isExpired()) {
			jwtTokenStore.removeAccessToken(accessToken);
			throw new InvalidTokenException("Access token expired: " + accessTokenValue);
		} else {
			OAuth2Authentication result = jwtTokenStore.readAuthentication(accessToken);
			if (result == null) {
				throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
			} else {
				return result;
			}
		}
	}

	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		return this.jwtAccessTokenConverter.convertAccessToken(token, authentication);
	}

	public Map<String, ?> convertAccessToken(String accessTokenValue) {
		OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(accessTokenValue);
		return this.convertAccessToken(oAuth2AccessToken, this.loadAuthentication(accessTokenValue));
	}
}
