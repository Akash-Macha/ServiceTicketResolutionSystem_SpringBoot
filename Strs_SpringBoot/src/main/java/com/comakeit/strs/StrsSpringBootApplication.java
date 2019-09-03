package com.comakeit.strs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.comakeit.strs.constants.Constants;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StrsSpringBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(StrsSpringBootApplication.class, args);
		Constants.url = "http://localhost:"+ ctx.getEnvironment().getProperty("server.port");
	}
}
