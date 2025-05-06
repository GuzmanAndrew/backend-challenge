package com.challenge.backend_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@EnableAsync
public class BackendChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendChallengeApplication.class, args);
	}

}
