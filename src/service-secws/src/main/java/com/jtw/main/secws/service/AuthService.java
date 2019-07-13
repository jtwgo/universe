package com.jtw.main.secws.service;

import com.jtw.main.secws.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    RestTemplate restTemplate;

    public String login(Map<String,String> params)
    {
        return restTemplate.postForObject("http://UNIFIED-CENTER/unified/sec/login", params, String.class);
    }
}
