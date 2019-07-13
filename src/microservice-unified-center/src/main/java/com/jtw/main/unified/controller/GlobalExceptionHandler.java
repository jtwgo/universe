package com.jtw.main.unified.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtw.main.unified.Exception.ParameterException;
import com.jtw.main.unified.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex)
    {
        ServletOutputStream outputStream = null;
        try
        {
            outputStream = response.getOutputStream();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(outputStream);
        Result result = new Result();
        response.setContentType("application/json");
        if (ex instanceof ParameterException)
        {

            result.setMessage(ex.getMessage());
            result.setResult(-1);
        }
        else
        {
            result.setResult(-2);
            result.setMessage(ex.getMessage());
        }
        String res;
        try
        {
            res = new ObjectMapper().writeValueAsString(result);
        }
        catch (JsonProcessingException e)
        {
            res = "json format exception";
            LOGGER.error(e.getMessage());
        }
        pw.write(res);
        pw.flush();
        LOGGER.error(ex.getMessage());

        return new ModelAndView();
    }
}
