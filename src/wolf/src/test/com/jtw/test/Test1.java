package com.jtw.test;

import org.junit.Test;

import java.util.Arrays;

public class Test1
{
    @Test
    public void byte2Hex()
    {
        StringBuilder hex = new StringBuilder();
        byte b = 1;
        boolean negative = false;
        System.out.println(Long.toBinaryString(Integer.MIN_VALUE));
        if (b < 0)
            negative = true;
        int tmp = Math.abs(b);
        if (negative) tmp = tmp | 0x80;
        String s = Integer.toHexString(tmp & 0xFF);
        if (s.length() == 1)
        {
            hex.append("0");
        }
        hex.append(s.toLowerCase());
        System.out.println(hex.toString());
    }

    @Test
    public void test1()
    {
        int i  = 10;
        byte[] bytes = new byte[4];
        bytes[0] = (byte)(i >> 24);
        bytes[1] = (byte)(i >> 16);
        bytes[2] = (byte)(i >> 8);
        bytes[3] = (byte)(i >> 0);
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void test2()
    {
        byte[] bytes = {-1, -1, -1, -10};
        int i = bytes[0] & 0xff << 24 |
        bytes[1] & 0xff << 16 |
        bytes[2] & 0xff << 8 |
        bytes[3] & 0xff << 0;
        System.out.println(i);
    }
}
