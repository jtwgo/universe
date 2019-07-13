package com.jtw.main.test.day1.day2;

import com.jtw.main.test.day1.day2.day2.Capacity;
import com.jtw.main.test.day1.day2.day2.Selector;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
//        Object[] ele = new Object[]{0,1,2,3,4,5,6,7,8,9};
//        Object[] dist = new Object[10];
//        System.arraycopy(ele,6,ele,5,ele.length-5-1);
//        System.out.println(Arrays.toString(ele));
//       String aa =  SingleClass.a;
//        Outer.main(new String[]{"hello","world"});
        Capacity capacity = new Capacity(10);
        for (int i = 0; i < capacity.getObjects().length; i++) {
            capacity.add(i);
        }
        Selector selector = capacity.getSelector();
        while(!selector.end()){
            System.out.println(selector.curent());
            selector.next();
        }
    }
}
