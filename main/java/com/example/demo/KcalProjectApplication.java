package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KcalProjectApplication extends SpringBootServletInitializer{

	
	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder build) {
		return build.sources(KcalProjectApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(KcalProjectApplication.class, args);
	}

}
