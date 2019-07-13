package com.jtw.main.unified.nio;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashSet;
import java.util.Iterator;

public class Server
{
    private ServerSocketChannel server;

    private Selector selector;

    private DataInputStream dataIn;

    private static int onlieNum = 0;

    private static final String INPUT_NAME = "please input your name:";

    private static final String USER_SPLIT = "#>:";

    private static final String NAME_EXIST = "the name is exist,please input your name:";

    private static final HashSet<String> onlineUser = new HashSet<>();

    public Server()
    {
        try {
            server = ServerSocketChannel.open();
            SocketAddress address = new InetSocketAddress(9090);
            server.bind(address);
            server.configureBlocking(false);
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void start() {
        Iterator<SelectionKey> iterator;
        SelectionKey key;
        while (true) {
            try {
                selector.select();
                iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    if (key.isValid())
                    {
                        if (key.isAcceptable())
                        {
                            SocketChannel accept = server.accept();
                            if (accept != null)
                            {
                                try
                                {
                                    accept.configureBlocking(false);
                                    accept.register(selector,SelectionKey.OP_READ);
                                    key.interestOps(SelectionKey.OP_ACCEPT);
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    buffer.put(INPUT_NAME.getBytes());
                                    buffer.flip();
                                    accept.write(buffer);
                                }
                                catch (Exception e)
                                {
                                    key.cancel();
                                    accept.close();
                                }

                            }


                        }
                        if (key.isReadable())
                        {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            StringBuilder sb = new StringBuilder();
                            int readNum = 0;
                            while ((readNum = channel.read(buffer))!= 0)
                            {
                                sb.append(new String(buffer.array(),0,readNum));
                            }
                            buffer.clear();
                            String receiveMsg = sb.toString();
                            String[] msgArray = receiveMsg.split(USER_SPLIT);
                            if (msgArray !=null && msgArray.length ==1)
                            {
                                if (onlineUser.contains(msgArray[0]))
                                {
                                    buffer.put(NAME_EXIST.getBytes());
                                    buffer.flip();
                                    channel.write(buffer);
                                    buffer.clear();
                                    continue;
                                }
                                else
                                {
                                    onlineUser.add(msgArray[0]);
                                    String hello = "welcome "+msgArray[0] + " join chat room";
                                    System.out.println(hello);
                                    broadCast(selector,null,hello);
                                    continue;
                                }

                            }
                            System.out.println(receiveMsg);
                            broadCast(selector,channel,receiveMsg);



                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    server.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    private void broadCast(Selector selector, SocketChannel except, String receiveMsg)
    {
        for (SelectionKey key : selector.keys())
        {
            Channel channel = key.channel();
            if (channel == except)
            {
                continue;
            }
            if (channel instanceof SocketChannel)
            {
                SocketChannel socketChannel = (SocketChannel)channel;
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put(receiveMsg.getBytes());
                buffer.flip();
                try {
                    socketChannel.write(buffer);
                    buffer.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args)
    {
        Server server = new Server();
        server.start();
    }
}
