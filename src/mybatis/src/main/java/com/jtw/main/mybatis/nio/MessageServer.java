package com.jtw.main.mybatis.nio;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class MessageServer
{
    public static Selector selector;

    private static ServerSocketChannel serverSocketChannel;

    private static final String CONFIGPATH = "D:\\jtw\\server.properties";

    private static final String SERVER_ADDRESS = "server_address";

    private static final String SERVER_PORT = "server_port";

    private static final int BUFFER_SIZE = 8192;
    static
    {
        InputStream in = null;
        try
        {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            Properties properties = new Properties();
            File configFile = new File(CONFIGPATH);
            if (!configFile.exists())
            {
                throw new FileNotFoundException("server properties is not found");
            }
            in = new FileInputStream(configFile);
            properties.load(in);
            String address = properties.getProperty(SERVER_ADDRESS);
            String port = properties.getProperty(SERVER_PORT);
            if (address == null || address.trim().isEmpty() || port == null || port.trim().isEmpty())
            {
                throw new IllegalArgumentException("server_address or port is empty");
            }
            SocketAddress socketAddress = new InetSocketAddress(address, Integer.parseInt(port));
            serverSocketChannel.bind(socketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        MessageServer messageServer = new MessageServer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    Set<SelectionKey> keys = MessageServer.selector.keys();
                    System.out.println("当前所有的keys数量：" + keys.size());
                    System.out.println("当前所有的keys:" + keys);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        messageServer.start();
    }

    private void start()
    {
        while (true)
        {
            try
            {
                int selectNum = selector.select();
                if (selectNum == 0) continue;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext())
            {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                SelectableChannel selectableChannel = selectionKey.channel();
                if (selectionKey.isAcceptable())
                {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) selectableChannel;
                    try
                    {
                        SocketChannel acceptChannel = serverChannel.accept();
                        acceptChannel.configureBlocking(false);
                        acceptChannel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE).attach("testKey");

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (selectionKey.isConnectable())
                {
                    Object attachment = selectionKey.attachment();
                    System.out.println("channel ["+ attachment +"]is connect!");
                }
                if (selectionKey.isReadable())
                {
                    System.out.println(selectableChannel.hashCode() + "this is readable");
                    handleMessage(selectableChannel);

                }
                if (selectionKey.isWritable())
                {
                    System.out.println(selectableChannel.hashCode() + "this is writable");
                    sendMessage(selectableChannel);
                }
                if (selectionKey.isWritable() || selectionKey.isReadable())
                {
                    selectionKey.cancel();
                }

            }
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(SelectableChannel selectableChannel)
    {
        //发送数据到客户端
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        String helloMessage ="[server]" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
                        +  ":欢迎进入服务端";
        SocketChannel socketChannel = (SocketChannel)selectableChannel;
        buffer.put(helloMessage.getBytes());
        buffer.flip();
        try
        {
            socketChannel.write(buffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                socketChannel.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }

    }

    private void handleMessage(SelectableChannel selectableChannel)
    {
        //从channel中读取客户端发过来的数据信息
        SocketChannel socketChannel = (SocketChannel) selectableChannel;
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try
        {
            socketChannel.read(buffer);
            buffer.flip();
            System.out.println("[client]" + new String(buffer.array(),0,buffer.limit()));
//            sendMessage(selectableChannel);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                socketChannel.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }

//        try
//        {
//            int readLen;
//            while ((readLen = socketChannel.read(buffer)) != -1)
//            {
//
//            }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
    }


}
