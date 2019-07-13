package com.jtw.main.mybatis.po;

public class Person
{
    private int num;

    private String name;

    private String pass;

    private String email;

    private int old;

    @Override
    public String toString() {
        return "Person{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", old=" + old +
                '}';
    }
}
