package com.jtw.main.mybatis.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ConsumerDemo
{

    private static Properties initProperties = new Properties();

    private static final String topic = "topic-demo";
//    private static final String topic = "__consumer_offsets";

    static
    {
        initProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.31.153:9092");
        initProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        initProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
//        initProperties.put(ProducerConfig.CLIENT_ID_CONFIG,"producer.client.id.demo1");
        initProperties.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
    }

    public static void main(String[] args) {
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(initProperties);
        consumer.subscribe(Collections.singletonList(topic));
        while (true)
        {
            ConsumerRecords<String, String> consumerRecord = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String,String> record : consumerRecord)
            {
                System.out.println("---------value------------");
                System.out.println(record.value());
            }
        }
    }


}
