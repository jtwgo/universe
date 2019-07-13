package com.jtw.main.unified.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 在此类中配置拦截器, 可添加拦截器栈
 * @author wenbronk
 * @time 2017年3月17日  下午5:01:38  2017
 */
@Configuration
public class MyWebMvcConfigureAdapter implements WebMvcConfigurer {

    /**
     * 此方法中添加连接器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}