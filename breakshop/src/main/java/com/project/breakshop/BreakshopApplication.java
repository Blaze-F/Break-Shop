package com.project.breakshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class BreakshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreakshopApplication.class, args);
	}

}
