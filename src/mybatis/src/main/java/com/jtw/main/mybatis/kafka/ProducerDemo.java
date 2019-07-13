package com.jtw.main.mybatis.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProducerDemo
{
    private static Properties initProperties = new Properties();

    private static final String topic = "topic-demo";

    static
    {
        initProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.31.153:9092");
        initProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        initProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        initProperties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,MyProducerInterceptor.class.getName());
//        initProperties.put(ProducerConfig.CLIENT_ID_CONFIG,"producer.client.id.demo1");
    }

    public static void main(String[] args) {
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(initProperties);
        ProducerRecord<String,String> producerRecord = new ProducerRecord<String, String>(topic,11,"test2","hello-kafka222");
        Future<RecordMetadata> send = kafkaProducer.send(producerRecord);
        try {
            RecordMetadata recordMetadata = send.get();
            System.out.println(recordMetadata);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
