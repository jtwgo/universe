package com.jtw.main.ssl;


import com.jtw.main.conf.PropertiesManager;
import com.jtw.main.thread.ThreadManager;
import com.jtw.main.utils.CommonUtils;
import com.jtw.main.utils.PasswordUtils;
import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.System.in;

public class SSLServer
{

    private int serverPort;
    private ServerSocket serverSocket;
    private static final int THREAD_NUMS = 15;
    private SSLServer(){
        try
        {
            Properties sslProperties = PropertiesManager.getInstance().getSslProperties();
            this.serverPort = Integer.parseInt(sslProperties.getProperty("ssl.port"));
            serverSocket = WolfSSLContext.getInstance().getSslContext().getServerSocketFactory().
                    createServerSocket(serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final SSLServer instance = new SSLServer();
    public static SSLServer getInstance()
    {
        return instance;
    }


    public void start()
    {

        if(null == serverSocket)
        {
            throw new RuntimeException("SSLServerSocket has not been initialized!");
        }
        if(serverSocket instanceof SSLServerSocket)
        {
            try
            {
                while(true)
                {
                    SSLSocket socket = (SSLSocket) serverSocket.accept();
                    ThreadManager.getInstance().add(new Worker(new Handler(),socket));
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        else
        {
            CommonUtils.close(serverSocket);
            throw new RuntimeException("the serverSocket is not the SSLServerSocket");
        }

    }

    public void shutdown()
    {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Worker implements Runnable
    {
        private Handler handler;
        private Socket socket;
        public Worker(Handler handler,Socket socket)
        {
            this. handler = handler;
            this.socket = socket;
        }

        @Override
        public void run() {
            handler.handle(socket);
        }
    }

    private static class Handler
    {

        public void handle(Socket socket) {
            ObjectInputStream ois = null;
            ObjectOutputStream oos = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                System.out.println("server accept message:"+ois.readObject());
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject("hello");
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            finally
            {
                    CommonUtils.close(ois);
                    CommonUtils.close(oos);
                    CommonUtils.close(socket);


            }
        }
    }

}

