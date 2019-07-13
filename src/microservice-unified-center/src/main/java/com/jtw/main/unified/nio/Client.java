package com.jtw.main.unified.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client
{
    private SocketChannel client;

    private Selector selector;

    private String name;

    private static final String USER_SPLIT = "#>:";

    private static final String NAME_EXIST = "the name is exist,please input your name:";

    private static final InetSocketAddress ADDRESS = new InetSocketAddress("localhost",2222);

    public Client()
    {
        try {
            client = SocketChannel.open(ADDRESS);
            client.configureBlocking(false);
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start()
    {
        readMsg();
        Scanner sc = new Scanner(System.in);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (sc.hasNextLine())
        {
            String content = sc.nextLine();
            if (name == null)
            {
                if (content.trim().isEmpty())
                {
                    System.out.println("name is invalid,please input again:");
                    continue;
                }
                name = content;
                buffer.put((name + USER_SPLIT).getBytes());
                buffer.flip();
                try {
                    client.write(buffer);
                    buffer.clear();
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        client.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            buffer.clear();
            buffer.put((name + USER_SPLIT + content).getBytes());
            buffer.flip();
            try {
                client.write(buffer);
                buffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void readMsg()
    {
        new Thread(new Handler()).start();
    }

    private class Handler implements Runnable
    {

        @Override
        public void run()
        {
            try
            {
                while (true)
                {
                    int select = selector.select();
                    if (select == 0)
                        continue;
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext())
                    {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isValid())
                        {
                            if (key.isReadable())
                            {
                                SocketChannel channel = (SocketChannel) key.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int readNum = 0;
                                StringBuilder sb = new StringBuilder();
                                try
                                {
                                    while ((readNum = channel.read(buffer))!= 0)
                                    {
                                        sb.append(new String(buffer.array(),0,readNum));
                                    }
                                    String receiveMsg = sb.toString();
                                    if (NAME_EXIST.equals(receiveMsg))
                                    {
                                        System.out.println(NAME_EXIST);
                                        name = null;
                                        continue;
                                    }
                                    System.out.println(receiveMsg);
                                }
                                catch (IOException e1)
                                {
                                    e1.printStackTrace();
                                    key.cancel();
                                    try
                                    {
                                        channel.close();
                                    }
                                    catch (IOException e2)
                                    {
                                        e2.printStackTrace();
                                    }
                                }

                            }
                        }
                    }
                }
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
