package com.jtw.main.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Test {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args)
    {
        Date date = new Date();
        String format = DATE_FORMAT.format(date);
        String name = "jtw";
        String s = name+","+format;
        System.out.println(s);
        try {
            DATE_FORMAT.parse(s.split(",")[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
