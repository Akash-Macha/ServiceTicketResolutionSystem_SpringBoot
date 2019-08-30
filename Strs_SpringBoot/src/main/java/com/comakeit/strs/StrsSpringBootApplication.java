package com.comakeit.strs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.comakeit.strs.constants.Constants;
import com.comakeit.strs.entites.Role;
import com.comakeit.strs.repositories.IRoleRepository;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StrsSpringBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(StrsSpringBootApplication.class, args);
		Constants.url = "http://localhost:"+ ctx.getEnvironment().getProperty("server.port");
		

	}

}
