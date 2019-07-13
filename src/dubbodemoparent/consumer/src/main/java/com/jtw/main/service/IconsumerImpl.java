package com.jtw.main.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.jtw.main.IAPIService;

@Service
public class IconsumerImpl implements IconsumerInter
{
    @Reference(version = "1.0.0")
    private IAPIService iapiService;

    @Override
    public String hello()
    {
        return iapiService.hello();
    }
}
