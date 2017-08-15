package com.orrin.sca.common.service.apigateway;

import com.orrin.sca.common.service.apigateway.filter.PreFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author orrin.zhang on 2017/7/28.
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class CommonServiceApigatewayApplication {

	@Bean
	public PreFilter myFilter(){
		return new PreFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(CommonServiceApigatewayApplication.class, args);
	}
}
