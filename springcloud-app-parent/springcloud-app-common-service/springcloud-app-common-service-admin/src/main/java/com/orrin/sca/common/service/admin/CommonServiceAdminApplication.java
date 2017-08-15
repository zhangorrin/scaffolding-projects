package com.orrin.sca.common.service.admin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author orrin.zhang on 2017/7/31.
 */
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableAdminServer
public class CommonServiceAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommonServiceAdminApplication.class, args);
	}
}
