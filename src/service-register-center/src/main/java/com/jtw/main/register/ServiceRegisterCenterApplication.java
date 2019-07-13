package com.jtw.main.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegisterCenterApplication
{

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegisterCenterApplication.class);
	public static void main(String[] args)
	{
		SpringApplication.run(ServiceRegisterCenterApplication.class, args);
		LOGGER.error("service register center is start successfully.");
	}
}
