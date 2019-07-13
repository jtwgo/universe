package com.jtw.main.unified.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.*;

public class SocketServer
{

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5506);
        while (true)
        {
            try {
                Socket accept = serverSocket.accept();
                Handle handle = createHandle(MutiHandler.class, accept);
//                Handle handle = createHandle(SingleHandler.class, accept);
                handle.handle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static  Handle createHandle(Class<? extends BaseHandler> clazz,Socket socket)
    {
        BaseHandler baseHandler = null;
        try {
            baseHandler = clazz.newInstance();
            baseHandler.setSocket(socket);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return baseHandler;
    }
}

class MutiHandler extends BaseHandler implements Runnable
{
    static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 15, 0
            , TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("SocketServer--io--");
            return t;
        }
    }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.err.println("你呗拒绝啦--------------------------------------");
        }
    });
    @Override
    public void run()
    {
        resolve();
    }

    @Override
    public void handle()
    {
        pool.execute(this);
    }
}

class SingleHandler extends BaseHandler
{
    @Override
    public void handle() {
        resolve();
    }
}

interface Handle
{
    void handle();
}

abstract class BaseHandler implements Handle
{
    private static int taskNum=0;
    protected Socket socket;

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }
    public void resolve()
    {
//        synchronized (BaseHandler.class){
            taskNum++;
            System.out.println(Thread.currentThread().getThreadGroup()+ "--" +Thread.currentThread().getName() + Thread.currentThread().getId() + " taskNum:" + taskNum);
//        }
        InputStream inputStream;
        OutputStream outputStream;
        BufferedReader buffin;
        PrintWriter pw;
        try
        {
            for (int i = 1; i < 4; i++) {
                Thread.sleep(1000 * i);
            }

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            buffin = new BufferedReader(new InputStreamReader(inputStream));
            String lin = buffin.readLine();
            System.out.println(lin);
            pw = new PrintWriter(outputStream);
            pw.write(Thread.currentThread().getName()+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(Calendar.getInstance().getTime()) + "你好我是java" );
            pw.flush();
            outputStream.close();
            pw.close();
            inputStream.close();
            buffin.close();
            socket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
