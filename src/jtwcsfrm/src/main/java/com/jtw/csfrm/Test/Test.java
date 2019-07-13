package com.jtw.csfrm.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class Test extends Observable implements Runnable, Observer {
    @Override
    public void run() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public static void main(String[] args) throws IOException {
        Selector listener = Selector.open();
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(2222));
        server.configureBlocking(false);
        server.register(listener, SelectionKey.OP_ACCEPT);
        boolean init = false;
        while (true)
        {
            int select = listener.select();
            System.out.println("server working.....");
            if (select > 0)
            {
                Set<SelectionKey> selectionKeys = listener.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext())
                {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable())
                    {
                        System.out.println("accept....");
                        SocketChannel accept = server.accept();

//                        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                        System.out.println("-------------SelectionKey.OP_READ");
                        if (!init)
                        {
                            new Thread(new Listener(selector)).start();
                            init = true;
                        }
                    }
                }
            }
        }

    }
}


class Listener implements Runnable
{
    Selector selector;
    public Listener(Selector selector)
    {
        this.selector = selector;
    }
    @Override
    public void run()
    {
        int count = 0;
        while (true)
        {
            int select = 0;
            try {
                select = selector.select(10000);
                System.out.println("listener working....." + count++);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (select > 0)
            {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext())
                {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isReadable())
                    {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        try {
                            channel.read(buffer);
                            System.out.println(new String(buffer.array()));
                            buffer.clear();
                            SocketChannel client = SocketChannel.open(new InetSocketAddress(2221));
                            client.configureBlocking(false);
                            buffer.put("你好,我是server".getBytes());
                            buffer.flip();
                            client.write(buffer);
                            continue;
                        } catch (IOException e) {
                            try {
                                System.out.println("channel close .....");
                                channel.close();
                                selectionKey.cancel();
                                continue;
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }

                    }
                }
            }
        }
    }
}
