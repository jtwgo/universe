package com.jtw.main.unified.entity;

import java.io.Serializable;

public class Result implements Serializable
{
    private int result;

    private Object message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "result:" + result +
                ", message:'" + message + '\'' +
                '}';
    }
}
