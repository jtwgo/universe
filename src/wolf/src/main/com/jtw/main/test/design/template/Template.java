package com.jtw.main.test.design.template;

public abstract class Template {
    public abstract String getResult();
    public abstract int getCode();
    public void commonHandle(){
        String result = this.getResult();
        int code = this.getCode();
        System.out.println("结果是："+result+"\n"+"错误码："+code);
    }
}
