package com.jtw.main.test.day1.day2;

import java.util.Arrays;

public class Outer {
     private class Inner{
        private Inner(){
            System.out.println("Inner()");
            test();
//            getInner();
        }
        public void test(){
            System.out.println("I am Inner test method!");
        }
    }
    public Inner getInner(){
        return new Inner();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        Inner inner = new Outer().getInner();
    }
}
