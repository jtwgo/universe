package com.jtw.main.controller;

import com.jtw.main.service.PersonService;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> test(@RequestBody Map<String, Object> params)
    {
        LOGGER.info("request info in request body:"+params);
        System.out.println("Controller receive:"+params);
        Map<String,Object> map = new HashMap<>();
        map.put("name","mrjiang");
        map.put("age","20");
        LOGGER.info("response content:"+map);
        return map;
    }
}
