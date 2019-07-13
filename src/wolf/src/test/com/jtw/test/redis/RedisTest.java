package com.jtw.test.redis;

import redis.clients.jedis.Jedis;

public class RedisTest
{

    public static void main(String[] args) throws InterruptedException {
        Jedis redis = new Jedis("192.168.31.61",6379);
        System.out.println(redis.get("name"));
        redis.setex("test",1,"test_value");
        Long name = redis.del("name");
        System.out.println(name);
        System.out.println(redis.get("test"));
        Thread.sleep(5000);
        System.out.println(redis.get("test"));
        Thread.sleep(6000);
        System.out.println(redis.get("test"));
    }
}
