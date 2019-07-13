package com.jtw.csfrm.rpc;

import java.util.*;

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
    }

    /**
     *
     * @param interfaceClazz 接口名称
     * @param <T> 泛型类
     * @return 接口对应的实现类代理类
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T> T lookup(Class<T> interfaceClazz)
    {
        //根据查询到的实现类，创建代理类
        return ServerProxy.createProxy(interfaceClazz);
    }

}
