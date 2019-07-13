package com.jtw.main.unified.csfrm.dao;


import com.jtw.main.unified.csfrm.common.Request;
import com.jtw.main.unified.csfrm.common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;

public class ServerProxy
{
    public static <T> T createProxy(Class<T> clazz, String interfaceName)
    {
        T proxyInstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                new ProxyInvovationHandler(interfaceName));
        return proxyInstance;
    }

    private static class ProxyInvovationHandler implements InvocationHandler
    {

        private String interfaceName;

        private LinkedList linkedList = new LinkedList();

        public ProxyInvovationHandler(String interfaceName)
        {
            this.interfaceName = interfaceName;
        }
        public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
        {
            //1.封装request
            Request request = new Request();
            request.setInterfaceName(interfaceName);
            request.setMethodName(method.getName());
            request.setParameters(args);
            Class<?>[] parameterTypes = method.getParameterTypes();
            request.setParameterTypes(parameterTypes);
            //2.发送request到server端得到响应结果.
            Response response = handleRequest(request);
            System.out.println(response);
            Object data = response.getData();
            return data;
        }

        private Response handleRequest(Request request)
        {
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress("localhost",12345));
                socket.setSoTimeout(5000);
                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(request);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Response response = (Response) ois.readObject();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }


    }
}
