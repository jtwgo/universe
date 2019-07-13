package com.jtw.csfrm.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Test1
{
    static Selector selector;

    public Test1() throws IOException
    {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(2221));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public static void main(String[] args) throws IOException
    {
        new Test1();
        SocketChannel client = SocketChannel.open(new InetSocketAddress(2222));
        client.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        new Thread(new Task(selector)).start();
        while (true)
        {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            buffer.put(line.getBytes());
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        }

    }
    private static class Task implements Runnable
    {
        Selector selector ;
        public Task(Selector selector)
        {
            this.selector = selector;
        }
        @Override
        public void run()
        {
            while (true)
            {
                try {
                    int select = selector.select(1000);
                    if (select > 0)
                    {
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext())
                        {
                            SelectionKey key = iterator.next();
                            iterator.remove();
                            if (key.isAcceptable())
                            {
                                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                                SocketChannel accept = channel.accept();
                                key.interestOps(SelectionKey.OP_ACCEPT);
                                accept.configureBlocking(false);
                                accept.register(selector, SelectionKey.OP_READ);
                            }
                            if (key.isReadable())
                            {
                                SocketChannel channel = (SocketChannel) key.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int read = 0;
                                System.out.println("----------------------work---------");
                                StringBuilder sb = new StringBuilder();
                                sb.append(new String(buffer.array()));
//                                while ((read = channel.read(buffer)) == -1)
//                                {
//                                    buffer.flip();
//
//                                    buffer.clear();
//                                }
                                System.out.println(sb.toString());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
