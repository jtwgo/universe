package com.jtw.main.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootConfiguration
@ImportResource({"classpath*:dubbo-provider.xml"})
@PropertySource({"classpath:dubbo.properties"})
public class Configuration {

}
