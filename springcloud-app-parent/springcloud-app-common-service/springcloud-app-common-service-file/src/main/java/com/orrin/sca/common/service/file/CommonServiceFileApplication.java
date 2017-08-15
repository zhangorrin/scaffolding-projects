package com.orrin.sca.common.service.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;

/**
 * @author orrin.zhang on 2017/8/14.
 * 文件服务器
 */
@Configuration
@EnableOAuth2Sso
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableHystrix
public class CommonServiceFileApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommonServiceFileApplication.class, args);
	}
}
