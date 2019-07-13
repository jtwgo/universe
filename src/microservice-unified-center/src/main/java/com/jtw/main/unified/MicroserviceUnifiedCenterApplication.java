package com.jtw.main.unified;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//@ComponentScan(basePackages = "com.jtw.main.unified")
@ServletComponentScan(basePackages = "com.jtw.main.unified")
//@EnableAutoConfiguration
public class MicroserviceUnifiedCenterApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(MicroserviceUnifiedCenterApplication.class, args);
	}
}
