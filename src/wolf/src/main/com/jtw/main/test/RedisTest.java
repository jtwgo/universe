package com.jtw.main.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtw.main.utils.PasswordUtils;
import com.jtw.main.utils.RestClient;
import org.apache.commons.io.LineIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisTest
{
    private static final String HOST = "localhost";
    private static  Jedis redis = new Jedis(HOST);
    private static final String AUTH_USER = "X-Auth-User";
    private static final String AUTH_PASSWORD = "X-Auth-Password";
    private static final String AUTH_TOKEN = "X-Auth-Token";
    public static void main(String[] args)
    {
//        redis.lpush("list","ssd");
//        redis.lpush("list","sas");
//        redis.lpush("list","sata");
//        List<String> list = redis.lrange("list", 0, 2);
//        System.out.println(list);
//        for (int i = 0; i <list.size() ; i++) {
//            System.out.println(list.get(i));
//        }

        String token = getAuthToken();
        System.out.println(token);
        if(token==null)
        {
            System.out.println("获取token失败");
        }
//        String token = "3E49018EC114DADAC7FD456F75D0134DF7185EDEF0881DAB74C7D954D2AEB54087B6BEC698B4311CB2EEB8376959CF7EE20EAA15631B16219FA1A59888B3CC50";
        doService(token);
        String s = PasswordUtils.decryptByAES256(token);
        System.out.println(s);


    }

    private static void doService(String token)
    {
        RestClient restClient = new RestClient("/home/mrjiang/tomcat_cert/rootCA.crt");
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPort(6443).setHost("127.0.0.1").setScheme("https").setPath("/wolf/service/test");
        try {
            Map<String,Object> map = new HashMap<>();
            Map<String,String> headers = new HashMap<>();
            map.put("hello","world");
            headers.put(AUTH_TOKEN,token);
            String body = new ObjectMapper().writeValueAsString(map);
            HttpResponse response = restClient.doPost(uriBuilder.build().toString(),headers,body);

            if(response.getStatusLine().getStatusCode()==200)
            {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
                LineIterator line = new LineIterator(reader);
                if(line.hasNext())
                {
                    System.out.println(line.next());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAuthToken()
    {
        RestClient restClient = new RestClient("/home/mrjiang/tomcat_cert/rootCA.crt");
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPort(22560).setHost("127.0.0.1").setScheme("https").setPath("/unified/security/auth");
        try {
            Map<String,String> headers = new HashMap<>();
            headers.put(AUTH_USER,"mrjiang");
            headers.put(AUTH_PASSWORD,"123456");
            HttpResponse response = restClient.doGet(uriBuilder.build().toString(),headers);
            if(response.getStatusLine().getStatusCode()==200)
            {
                return response.getFirstHeader("X-Auth-Token").getValue();
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
