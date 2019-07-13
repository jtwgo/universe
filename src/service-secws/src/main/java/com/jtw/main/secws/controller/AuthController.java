package com.jtw.main.secws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtw.main.secws.Exception.ParameterException;
import com.jtw.main.secws.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于用户鉴权和token认证.
 */
@Controller
@RequestMapping(path = "/unified/sec")
public class AuthController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private static final String AUTH_USER = "X-Auth-User";
    private static final String AUTH_PASSWORD = "X-Auth-Password";
    private static final String AUTH_TOKEN = "X-Auth-Token";
    private static final String LOG_PREFIX = "[Unified-Secws]";

    @Autowired
    private AuthService authService;

    /**
     * 登录获取token
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/login")
//    @ResponseBody
    public void login(HttpServletRequest request, HttpServletResponse response)
    {
        final String username = request.getHeader(AUTH_USER);
        final String password = request.getHeader(AUTH_PASSWORD);
        //只是校验了参数是否为空,还应该校验用户名和密码的合法性
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            LOGGER.error(LOG_PREFIX + "request userName or password is empty");
            throw new ParameterException(LOG_PREFIX + "request header userName or password is empty");
        }
        Map<String,String> loginParams = new HashMap<>();
        loginParams.put(AUTH_USER, username);
        loginParams.put(AUTH_PASSWORD, password);
        String result = authService.login(loginParams);
        System.out.println(result);
        Map res;
        try
        {
             res= new ObjectMapper().readValue(result, Map.class);
        }
        catch (IOException e)
        {
            throw new RuntimeException("json parse failed.");
        }
        Map<String,String> message = (Map<String, String>) res.get("message");
        response.setHeader(AUTH_TOKEN, message.get(AUTH_TOKEN));
    }

}

