package com.jtw.main.unified.Exception;

public class ParameterException extends RuntimeException
{
    public ParameterException(String message)
    {
        super(message);
    }

    @Override
    public String getMessage()
    {
        return super.getMessage();
    }
}
