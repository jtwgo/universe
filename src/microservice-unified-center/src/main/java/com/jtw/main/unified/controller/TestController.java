package com.jtw.main.unified.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController
{
    @Value("${server.port}")
    String port;

    @RequestMapping(value = "/hi" )
    @ResponseBody
    public String hi(@RequestParam String name)
    {
        return "hi:"+name+",I am from"+port;
    }
}
