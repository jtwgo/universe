package com.jtw.main.secws.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class CrossSiteFiter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletResponse resp = (HttpServletResponse) response;
        //允许跨域时使用的请求地址
        resp.setHeader("Access-Control-Allow-Origin", "*");
        //允许跨域时使用的请求方法
        resp.addHeader("Access-Control-Allow-Methods","POST");
        //允许跨域时使用的请求头信息
        resp.addHeader("Access-Control-Allow-Headers", "X-Auth-Password,X-Auth-User,Authorization,Origin, X-Requested-With, Content-Type, Accept");
        //允许跨域时展示的响应头信息
        resp.addHeader("Access-Control-Expose-Headers", "X-Auth-Token,X-Auth-User,Authorization,Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
