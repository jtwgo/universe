package com.jtw.main.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jtw.main.IAPIService;

@Service(version = "1.0.0")
public class ProviderImpl implements IAPIService {


    @Override
    public String hello() {
        System.out.println("服务提供者正在提供服务..");
        return "{\"result\":\"hello dubbo!\"}";
    }
}
