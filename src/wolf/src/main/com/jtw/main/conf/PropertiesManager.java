package com.jtw.main.conf;

import com.jtw.main.utils.CommonUtils;

import java.io.File;
import java.util.Properties;

public class PropertiesManager {
    private volatile boolean isInit = false;
    private Properties sslProperties;
    private String sslPropertiesPath = CommonUtils.getWebDir()+"WEB-INF"+ File.separator+"ssl.properties";
    private PropertiesManager(){}
    private static PropertiesManager instance = new PropertiesManager();
    public static PropertiesManager getInstance()
    {
        return instance;
    }

    public boolean init()
    {
        if(isInit)
        {
            return isInit;
        }
        sslProperties = CommonUtils.loadProperties(sslPropertiesPath);
        return isInit=true;
    }
    public boolean refresh()
    {
        isInit = false;
        return init();
    }
    public Properties getSslProperties()
    {
        return sslProperties;
    }
}
