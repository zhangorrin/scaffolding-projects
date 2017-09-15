package com.orrin.sca.common.service.uaa;

import com.orrin.sca.component.jpa.dao.BaseJPARepositoryImpl;
import com.orrin.sca.component.jpa.parent.ParentApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author orrin.zhang on 2017/7/28.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableJpaRepositories(repositoryBaseClass = BaseJPARepositoryImpl.class)
public class CommonServiceUaaApplication extends ParentApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommonServiceUaaApplication.class, args);
	}

	@RestController
	@RequestMapping("/")
	public static class UaaController {

		private static final Logger logger = LoggerFactory.getLogger(UaaController.class);

		@RequestMapping("index")
		public String index(){
			return "hello uaa controller";
		}
	}

}
