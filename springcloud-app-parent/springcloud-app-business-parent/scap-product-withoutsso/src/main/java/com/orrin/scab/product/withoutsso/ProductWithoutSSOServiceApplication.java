package com.orrin.scab.product.withoutsso;

import com.orrin.sca.component.jpa.dao.BaseJPARepositoryImpl;
import com.orrin.sca.component.jpa.parent.ParentApplication;
import com.orrin.sca.component.privilege.annotation.ResourcePrivilegeEntity;
import com.orrin.sca.component.privilege.processor.ResourcePrivilegeListenerProcessor;
import com.orrin.sca.component.utils.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author orrin.zhang on 2017/7/28.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableHystrix
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryBaseClass = BaseJPARepositoryImpl.class)
@Import(SpringUtil.class)
public class ProductWithoutSSOServiceApplication extends ParentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductWithoutSSOServiceApplication.class, args);

		ResourcePrivilegeListenerProcessor resourcePrivilegeListenerProcessor = new ResourcePrivilegeListenerProcessor();

		List<ResourcePrivilegeEntity> resourcePrivilegeEntities = resourcePrivilegeListenerProcessor.getResourcePrivilegeByannotation();

		System.out.println(resourcePrivilegeEntities.toString());
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public AlwaysSampler defaultSampler(){
		return new AlwaysSampler();
	}

}
