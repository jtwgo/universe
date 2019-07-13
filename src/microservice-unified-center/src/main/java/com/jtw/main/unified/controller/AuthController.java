package com.jtw.main.unified.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtw.main.unified.Exception.ParameterException;
import com.jtw.main.unified.csfrm.dao.Server;
import com.jtw.main.unified.entity.Result;
import com.jtw.main.unified.entity.Token;
import com.jtw.main.unified.entity.User;
import com.jtw.main.unified.manager.TokenManager;
import com.jtw.main.unified.manager.UserManager;

import com.jtw.main.unified.utils.HttpUtils;
import com.jtw.main.unified.utils.PasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private static final String LOG_PREFIX = "[Unified-Manager]";
    @Autowired
    private UserManager userManager;

    /**
     * 验证token是否有效
     * @param request
     * @param response
     */
    @RequestMapping(value = "/verify",method = RequestMethod.GET)
    public void validateToken(HttpServletRequest request,HttpServletResponse response)
    {
        //检查请求信息
        boolean isLogin = isValid(request);
        if(!isLogin)
        {
            response.setStatus(401,"token is invalid");
            return;
        }
        LOGGER.info("{} has verify successful!",request.getRemoteHost());
        response.setStatus(200);
        return;
    }

    /**
     * 测试用户登录
     * login
     * @param request httpServletRequest
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody Map<String,String> params,HttpServletRequest request)
    {
        //1.检查参数
        checkUserPassword(params);
        String userName = params.get(AUTH_USER);
        String password = params.get(AUTH_PASSWORD);
        //2.校验用户名和密码
        User user= userManager.findByName(userName);
        if (user == null)
        {
            LOGGER.error("user is not exist");
            throw new ParameterException("user is not exist");
        }
        //3.检查密码是否正确
        String salt = user.getSalt();
        String curPassword = PasswordUtils.encryptBySha256(password, salt);
        String oldPassword = user.getPassword();
        if (!oldPassword.equals(curPassword))
        {
            LOGGER.error("password is not correct!");
            throw new ParameterException("password is not correct!");
        }
        //用户名,密码正确,生成token;
        String clientIp = HttpUtils.getRealRemoteAddr(request);
        String uuid = generateUUID();
        Token token = new Token(clientIp,userName, uuid);
        System.out.println(token);
        //添加token到缓存中,待完成.
        TokenManager.getInstance().addToken(token);
        Result result = new Result();
        result.setResult(0);
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put(AUTH_TOKEN,uuid);
        result.setMessage(responseBody);
        String res;
        try
        {
            res = new ObjectMapper().writeValueAsString(result);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }

        return res;
    }

    private void checkUserPassword(Map<String,String> params)
    {
    }

    private boolean isValid(HttpServletRequest request)
    {
        String token = request.getHeader(AUTH_TOKEN);
        if (token==null)
        {
            LOGGER.error("Auth-Token from request is null.");
            return false;
        }
        try
        {
            String token_str = PasswordUtils.decryptByAES256(token);
            String uuid = token_str.split(",")[1].split("=")[1];
            for (Token memory_token : TokenManager.getInstance().queryAllToken())
            {
                if(uuid.equals(memory_token.getUuid()))
                {
                    long createDate = memory_token.getCreateDate();
                    long differ_time = (System.currentTimeMillis()-createDate)/1000;
                    if(differ_time>180)
                    {
                        LOGGER.error("Auth-Token is expired.");
                        return false;
                    }
                    memory_token.refresh();
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("token is invalid");
            LOGGER.error("Exception message:"+e.getMessage());
            return false;
        }
        LOGGER.error("the Auth-Token is invalid");
        return false;
    }


    private String generateUUID() {
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid;
    }
}
