package com.jtw.main.secws.utils;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class CommonUtils
{

    private CommonUtils(){}
    public static Properties loadProperties(String propertiesPath)
    {
        if(null == propertiesPath)
        {
            throwException("propertiesPath is null");
        }
        File file = new File(propertiesPath);
        if(!file.exists())
        {
            throwException("no such file in propertiesPath");
        }
        FileInputStream fis = null;
        Properties prop = new Properties();
        try {
            fis = new FileInputStream(file);
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fis);
        }
        return prop;
    }

    private static void throwException(String msg)
    {
        throw new RuntimeException(msg);
    }

    public static void close(Closeable c )
    {
        if(null!=c)
        {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getWebDir()
    {
        URL url = CommonUtils.class.getResource("CommonUtils.class");
        String path = url.getPath();
        if(path!=null&&path.length()>0)
        {
            path = path.substring(0,path.indexOf("WEB-INF/"));
        }
        return path;
    }

}
