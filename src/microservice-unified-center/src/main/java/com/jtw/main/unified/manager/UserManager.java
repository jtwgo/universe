package com.jtw.main.unified.manager;

import com.jtw.main.unified.Exception.ParameterException;
import com.jtw.main.unified.csfrm.dao.Server;
import com.jtw.main.unified.entity.Role;
import com.jtw.main.unified.entity.User;
import com.jtw.main.unified.service.UserService;
import com.jtw.main.unified.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户管理
 */
@Component
@Scope(value = "singleton")
public class UserManager
{

    private static final String USERNAME_REG = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{8,32}";

    private static final String PASSWORD_REG = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{8,32}";

    private static final String CONSTS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private UserService userService;

    /**
     * 这里还有用户权限，用户名密码校验等还没有做
     *
     * */
    public Map<String,Object> addUser(Map<String,String> params)
    {

        checkParams(params);
        String username = params.get("username");
        String password = params.get("password");
        int roleId = Integer.parseInt(params.get("roleId"));
        User newUser = new User();
        newUser.setUsername(username);
        byte[] bytes = generateRandomBytes();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String salt = base64Encoder.encode(bytes);
        newUser.setPassword(PasswordUtils.encryptBySha256(password,salt));
        newUser.setRoleId(roleId);
        newUser.setSalt(salt);
        System.out.println(newUser);
        this.userService.addUser(newUser);
        Map<String,Object> result = new HashMap<>();
        result.put("result",0);
        return result;
    }

    private byte[] generateRandomBytes()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++)
        {
            SecureRandom random = new SecureRandom();
            int index = random.nextInt(CONSTS.length());
            char c = CONSTS.charAt(index);
            sb.append(c);
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public User findByName(String username)
    {
        Server.list.add(this.userService);
        return this.userService.findByName(username);
    }

    private void checkParams(Map<String, String> params)
    {
        String username = params.get("username");
        Pattern patternUserName = Pattern.compile(USERNAME_REG);
        Matcher matcherUserName = patternUserName.matcher(username);
        if (StringUtils.isEmpty(username) || !matcherUserName.matches())
        {
            LOGGER.error("param username:{},is invalid",username);
            throw new ParameterException("param username:"+username+",is invalid"+username);
        }
        String password = params.get("password");
        Pattern patternPwd = Pattern.compile(PASSWORD_REG);
        Matcher matcherPwd = patternPwd.matcher(password);
        if (StringUtils.isEmpty(password) || !matcherPwd.matches())
        {
            LOGGER.error("param password:**** is invalid");
            throw new ParameterException("param password:**** is invalid");
        }

        try
        {
            String roleId_str = params.get("roleId");
            if (StringUtils.isEmpty(roleId_str))
            {
                LOGGER.error("param roleId:{} is invalid",roleId_str);
                throw new ParameterException("param roleId:"+roleId_str+" is invalid");
            }
            int roleId = Integer.parseInt(roleId_str);
            boolean valid = false;
            Role[] roles = Role.values();
            for (Role role : roles)
            {
                if (role.getRoleId() == roleId)
                {
                    valid = true;
                    break;
                }
            }
            if (!valid)
            {
                LOGGER.error("param roleId is invalid,roleId={}",roleId);
                throw new ParameterException("param roleId is invalid,roleId="+roleId);
            }
        }
        catch (NumberFormatException e)
        {
            throw new ParameterException("param roleId is not a regular int value");
        }
    }
}
