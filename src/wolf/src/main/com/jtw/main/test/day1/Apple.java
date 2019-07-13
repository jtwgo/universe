package com.jtw.main.test.day1;

import java.util.ArrayList;
import java.util.List;

public class Apple <T extends Number> {
    private T size;
    public Apple(T size)
    {
        this.size = size;
    }

    public void setSize(T size) {
        this.size = size;
    }

    public T getSize() {
        return size;
    }
    public List<String> getApples(){
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            list.add(new Apple<Integer>(i*10).toString());

        }
        return list;
    }

    @Override
    public String toString() {
        return "Applce Size[size:]"+this.size;
    }

    public static void main(String[] args) {
        Apple<Integer> a = new Apple<Integer>(6);
        for (String apple: a.getApples()
             ) {
            System.out.println(apple);
        }
        Apple b = a;
        for (Object bb:b.getApples()
             ) {
            System.out.println(bb);
        }
//        List<String>[] list = new ArrayList<String>[10];
//        List[] aa = list;
    }
}
