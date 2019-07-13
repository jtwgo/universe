package com.jtw.main.unified.csfrm.dao;

import com.jtw.main.unified.service.UserService;
import com.jtw.main.unified.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 服务代理工厂,通过接口名称查询对应的接口
 */
public class ServiceProxyFactory
{

    private static Map<String, String> clazzImplCache;
    static
    {
        clazzImplCache = new HashMap<String, String>();
        //1.解析xml配置，将接口名称和实现类的名称存放到缓存中
        clazzImplCache.put(List.class.getName(), LinkedList.class.getName());
        clazzImplCache.put(UserService.class.getName(), UserServiceImpl.class.getName());
    }

    /**
     *
     * @param clazz 接口名称
     * @param <T> 泛型类
     * @return 接口对应的实现类代理类
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T> T lookup(Class<T> clazz)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String clazzName = clazz.getName();
        //1.根据传入的className查询配置文件中对应的实现类
        Class<?> clazzImpl = localLookup(clazzName);
        if (!clazz.isAssignableFrom(clazzImpl))
        {
            throw new IllegalArgumentException(clazzImpl.getName() + " is not extends from " + clazz.getName());
        }
        //根据查询到的实现类，创建代理类
        return (T) ServerProxy.createProxy(clazzImpl, clazz.getName());
    }

    private static Class<?> localLookup(String clazzName) throws ClassNotFoundException
    {
        String s = clazzImplCache.get(clazzName);
        Class<?> impl = Class.forName(s);
        return impl;
    }

    public static void main(String[] args) throws Exception {
        UserService userService = ServiceProxyFactory.lookup(UserService.class);
        System.out.println(userService.findByName("zhangsan"));
    }
}
