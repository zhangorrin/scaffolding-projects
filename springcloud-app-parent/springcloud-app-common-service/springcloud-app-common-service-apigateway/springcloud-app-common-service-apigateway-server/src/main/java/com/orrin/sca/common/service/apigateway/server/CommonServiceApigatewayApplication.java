package com.orrin.sca.common.service.apigateway.server;

import com.orrin.sca.common.service.apigateway.server.filter.PreFilter;
import com.orrin.sca.component.privilege.intercept.URLFilterInvocationAuthority;
import com.orrin.sca.component.redis.redisson.RedissonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author orrin.zhang on 2017/7/28.
 */
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {"com.orrin.sca"})
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.orrin.sca"})
@Import({RedissonConfig.class,URLFilterInvocationAuthority.class})
public class CommonServiceApigatewayApplication {

	@Bean
	public PreFilter myFilter(){
		return new PreFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(CommonServiceApigatewayApplication.class, args);
	}
}
