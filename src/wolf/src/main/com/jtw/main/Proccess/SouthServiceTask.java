package com.jtw.main.Proccess;

import com.jtw.main.ssl.SSLServer;

public class SouthServiceTask extends ProccessTask {
    private SSLServer server;
    public SouthServiceTask(SSLServer server)
    {
        this.server = server;
    }
    @Override
    public void execute() {
        server.start();
    }
}
