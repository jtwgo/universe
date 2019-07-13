package com.jtw.main.test;

public class RealMan implements GIveGIft{

    private String loveName;

    public RealMan(String loveName) {
        this.loveName = loveName;
    }

    public void sendFlower() {

        System.out.println("送"+this.loveName+"花");
    }

    public void sendCar() {
        System.out.println("送"+this.loveName+"车");
    }

    public void sendWatch() {
        System.out.println("送"+this.loveName+"手表");
    }

}
