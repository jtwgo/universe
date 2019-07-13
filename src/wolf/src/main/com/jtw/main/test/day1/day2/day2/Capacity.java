package com.jtw.main.test.day1.day2.day2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Capacity {
    private Object[] objects;
    private int next = 0;
    public Capacity(int length){
        objects = new Object[10];
    }
    public void add(int param){
        objects[next++] = param;
    }

    public Object[] getObjects() {
        return objects;
    }

    public Selector getSelector(){
        return new SelectorImpl();
    }
    private class SelectorImpl implements Selector{

        private int i = 0;
        @Override
        public boolean end() {
            return i == objects.length;
        }

        @Override
        public Object curent() {
            if (i<objects.length)
            {
                return objects[i];
            }
            else
            {
                return null;
            }
        }


        @Override
        public void next() {
            i++;
        }
    }

    public static void main(String[] args) {
//        Capacity capacity = new Capacity(10);
//        for (int i = 0; i < capacity.objects.length; i++) {
//            capacity.add(i);
//        }
//        Selector selector = capacity.getSelector();
//        while(!selector.end()){
//            System.out.println(selector.curent());
//            selector.next();
//
//        }
        List<Snow> list = Arrays.<Snow>asList(new Light(),new Heavy());
        System.out.println(list);
        new HashMap<String,String>();
    }

}
class Snow{}
class Powder extends Snow{}
class Light extends Powder{}
class Heavy extends Powder{}
