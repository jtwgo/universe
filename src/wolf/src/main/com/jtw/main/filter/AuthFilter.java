package com.jtw.main.filter;

import com.jtw.main.utils.RestClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String token = request.getHeader("X-Auth-Token");
        String secUrl = "https://127.0.0.1:22560/unified/security/verify";
        String cerPath = "/home/mrjiang/tomcat_cert/rootCA.crt";
        RestClient restClient = new RestClient(cerPath);
        Map<String,String> headers = new HashMap<>();
        headers.put("X-Auth-Token",token);
        HttpResponse response_auth = restClient.doGet(secUrl, headers);
        if(response_auth.getStatusLine().getStatusCode()!=200)
        {
            PrintWriter pw = new PrintWriter(servletResponse.getOutputStream());
            pw.write("Auth failed");
            pw.flush();
            pw.close();
            LOGGER.error("Auth failed,the token maybe invalid.");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
