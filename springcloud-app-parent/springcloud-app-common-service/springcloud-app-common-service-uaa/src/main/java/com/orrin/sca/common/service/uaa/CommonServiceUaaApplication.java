package com.orrin.sca.common.service.uaa;

import com.orrin.sca.component.jpa.dao.BaseJPARepositoryImpl;
import com.orrin.sca.component.jpa.parent.ParentApplication;
import com.orrin.sca.framework.core.exception.GlobalWebExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author orrin.zhang on 2017/7/28.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryBaseClass = BaseJPARepositoryImpl.class)
@EnableTransactionManagement
public class CommonServiceUaaApplication extends ParentApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommonServiceUaaApplication.class, args);
	}

	@ControllerAdvice
	public static class MyGlobalWebExceptionHandler extends GlobalWebExceptionHandler {

	}
}
