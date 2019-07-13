package com.jtw.csfrm.rpc;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerProxy
{
    private static Map<String,String> clazzImplCache = new HashMap<String,String>();

    public static <T> T createProxy(Class<T> interfaceClazz)
    {
        String interfaceName = interfaceClazz.getName();
        //1.根据传入的className查询配置文件中对应的实现类
        Object serviceImpl = localLookup(interfaceName);
        if (!interfaceClazz.isAssignableFrom(serviceImpl.getClass()))
        {
            throw new IllegalArgumentException(serviceImpl.getClass().getName()
                    + " is not extends from " + interfaceClazz.getName());
        }
        String realService = clazzImplCache.get(interfaceName);
        if (realService == null)
        {
            realService = remoteLookup(interfaceName);
        }
        Class<?> realServiceClazz = null;
        try
        {
            realServiceClazz = Class.forName(realService);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) Proxy.newProxyInstance(realServiceClazz.getClassLoader()
                , realServiceClazz.getInterfaces(),
                new ProxyInvovationHandler(interfaceName));
    }

    private static String remoteLookup(String interfaceName)
    {
        //查询到之后加对象加入到缓存中
        return null;
    }

    private static Object localLookup(String clazzName)
    {
        return clazzImplCache.get(clazzName);
    }

    private static class ProxyInvovationHandler implements InvocationHandler
    {

        private String interfaceName;

        public ProxyInvovationHandler(String interfaceName)
        {
            this.interfaceName = interfaceName;
        }

        public Object invoke(Object proxy, Method method, Object[] args)
        {
            //1.封装request
            Request request = new Request();
            request.setInterfaceName(interfaceName);
            request.setMethodName(method.getName());
            request.setParameters(args);
            request.setParameterTypes(method.getParameterTypes());
            //2.发送request到server端得到响应结果.
            Response response = handleRequest(request);
            Object data = response.getData();
            return data;
        }

        private Response handleRequest(Request request)
        {
            Socket socket = new Socket();
            try
            {
                socket.connect(new InetSocketAddress("localhost",12345));
                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(request);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Response response = (Response) ois.readObject();
                return response;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            return null;
        }


    }
}
