package com.jtw.main.unified.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    public static String getRealRemoteAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        LOGGER.info("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip))
        {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
            LOGGER.info("Proxy-Client-IP ip: " + ip);
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            LOGGER.info("WL-Proxy-Client-IP ip: " + ip);
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            LOGGER.info("HTTP_CLIENT_IP ip: " + ip);
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            LOGGER.info("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            LOGGER.info("X-Real-IP ip: " + ip);
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            LOGGER.info("getRemoteAddr ip: " + ip);
        }
        LOGGER.info("获取客户端ip: " + ip);
        return ip;
    }
}
