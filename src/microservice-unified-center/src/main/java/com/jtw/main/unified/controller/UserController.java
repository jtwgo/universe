package com.jtw.main.unified.controller;

import com.jtw.main.unified.manager.UserManager;
import com.jtw.main.unified.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jtw.main.unified.utils.PasswordUtils;
import com.jtw.main.unified.entity.Token;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/unified/sec")
public class UserController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addUser(@RequestBody Map<String,String> bodyParam)
    {
        Map<String,Object> result = userManager.addUser(bodyParam);
        return result;
    }
}
