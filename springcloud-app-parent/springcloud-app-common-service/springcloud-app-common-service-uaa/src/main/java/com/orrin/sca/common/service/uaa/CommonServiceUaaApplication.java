package com.orrin.sca.common.service.uaa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author orrin.zhang on 2017/7/28.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class CommonServiceUaaApplication {
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
