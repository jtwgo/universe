package com.jtw.main.unified.csfrm.dao;


import com.jtw.main.unified.csfrm.common.Request;
import com.jtw.main.unified.csfrm.common.Response;
import com.jtw.main.unified.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server
{
    public static List<UserService> list = new ArrayList<>();
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        while (true)
        {

            try {
                Socket accept = serverSocket.accept();
                InputStream in = accept.getInputStream();
                ObjectInputStream ois= new ObjectInputStream(accept.getInputStream());
                Object o = null;
                o = ois.readObject();
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
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }


        }

    }
    private Response handleRequest(Request request)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Method method = Class.forName(request.getInterfaceName())
                .getDeclaredMethod(request.getMethodName(), request.getParameterTypes());
        Object data = method.invoke(list.get(0), request.getParameters());
        Response response = new Response();
        response.setCode(0);
        response.setData(data);
        return response;
    }
}
