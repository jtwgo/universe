package com.jtw.main.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetCharacterEncoding implements Filter
{

    private String enCoding = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        enCoding = filterConfig.getInitParameter("enCoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        request.setCharacterEncoding(enCoding);
        filterChain.doFilter(servletRequest,servletResponse);
        response.setCharacterEncoding(enCoding);
    }

    @Override
    public void destroy() {
        enCoding = null;
    }
}