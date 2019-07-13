package com.jtw.csfrm.rpc;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server
{
    private Map<String, String> serviceImplCache;
    public Server()
    {
        serviceImplCache = new HashMap<String, String>();
    }
    public void start() throws IOException, ClassNotFoundException
    {
        ServerSocket serverSocket = new ServerSocket(12345);
        while (true)
        {
            Socket accept = serverSocket.accept();
            InputStream in = accept.getInputStream();
            ObjectInputStream ois= new ObjectInputStream(accept.getInputStream());
            Object o = ois.readObject();
            Request request = null;
            if (o instanceof Request)
            {
                request = (Request)o;
            }
           Response response = handleRequest(request);
           ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream());
           oos.writeObject(response);
           oos.flush();
           oos.close();
           in.close();

        }

    }
    private Response handleRequest(Request request)
    {
        String interfaceName = request.getInterfaceName();
        if (serviceImplCache.get(interfaceName) == null)
        {
            throw new RuntimeException("cannot find impl for interface " + interfaceName);
        }
        Method method;
        Response response = new Response();
        try {
            method = Class.forName(interfaceName)
                    .getDeclaredMethod(request.getMethodName(), request.getParameterTypes());
            Object data = method.invoke(serviceImplCache.get(interfaceName), request.getParameters());
            response.setCode(0);
            response.setData(data);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }
}
