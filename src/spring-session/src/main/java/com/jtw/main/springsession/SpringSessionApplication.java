package com.jtw.main.springsession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@EnableAutoConfiguration
//@SpringBootApplication
@RestController
public class SpringSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSessionApplication.class, args);
    }

    @Value("${server.port}")
    private String port;

    @RequestMapping("/getSession")
    public String getSession(HttpServletRequest request, String key)
    {

        HttpSession session = request.getSession();
        String id = session.getId();
        Object value = session.getAttribute(key);
        return "server-port:"+port+",id:"+id+",value"+value;

    }

    @RequestMapping("/setSession")
    public String setSession(HttpServletRequest request, String key,String value)
    {

        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        return "server-port:"+port+" success";

    }
}
