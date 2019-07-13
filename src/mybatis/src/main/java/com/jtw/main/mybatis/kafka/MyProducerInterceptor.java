package com.jtw.main.mybatis.kafka;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class MyProducerInterceptor implements ProducerInterceptor<String,String> {
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record)
    {
        System.out.println("on send ..........");

        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception)
    {
        System.out.println("on onAcknowledgement....");
//        System.exit(0);
    }

    @Override
    public void close()
    {

        System.out.println("close..");
    }

    @Override
    public void configure(Map<String, ?> configs)
    {
        for(Map.Entry entry : configs.entrySet())
        {
            System.out.println(entry.getKey()+"----"+entry.getValue());
        }
    }
}
