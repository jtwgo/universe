package com.jtw.main.Proccess;

public abstract class ProccessTask implements Runnable {
    @Override
    public void run() {
        execute();
    }

    public abstract void execute();
}
