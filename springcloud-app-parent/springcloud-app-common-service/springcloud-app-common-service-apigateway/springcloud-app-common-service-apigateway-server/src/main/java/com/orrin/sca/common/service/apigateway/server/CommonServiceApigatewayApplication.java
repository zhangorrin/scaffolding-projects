package com.orrin.sca.common.service.apigateway.server;

import com.orrin.sca.common.service.apigateway.server.filter.PreFilter;
import com.orrin.sca.component.jpa.dao.BaseJPARepositoryImpl;
import com.orrin.sca.component.jpa.parent.ParentApplication;
import com.orrin.sca.component.redis.cache.RedisCacheConfig;
import com.orrin.sca.component.redis.redisson.RedissonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author orrin.zhang on 2017/7/28.
 */
@SpringBootApplication
@EnableZuulProxy
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableEurekaClient
@EnableHystrix
@EnableFeignClients(basePackages = {"com.orrin.sca"})
@ComponentScan(basePackages = {"com.orrin.sca"})
@EntityScan(basePackages = {"com.orrin.sca"})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryBaseClass = BaseJPARepositoryImpl.class)
@EnableTransactionManagement
@EnableCaching
@Import({RedissonConfig.class, RedisCacheConfig.class})
public class CommonServiceApigatewayApplication extends ParentApplication {

	@Bean
	public PreFilter myFilter(){
		return new PreFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(CommonServiceApigatewayApplication.class, args);
	}

}
