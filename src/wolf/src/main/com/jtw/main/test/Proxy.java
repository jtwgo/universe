package com.jtw.main.test;

public class Proxy implements GIveGIft{

    private RealMan realMan;

    public Proxy(RealMan realMan)
    {
        this.realMan = realMan;
    }


    public void sendFlower() {
        this.realMan.sendFlower();
    }

    public void sendCar() {
        this.realMan.sendCar();
    }

    public void sendWatch() {
        this.realMan.sendWatch();
    }

}
