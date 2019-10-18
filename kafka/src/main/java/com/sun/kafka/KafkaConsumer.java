package com.sun.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

/**
 * 消费者
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "testTopic")
    public void listen(ConsumerRecord<?, ?> record){
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
        String topic = record.topic();
        long offset = record.offset();
        Object value = record.value();
        System.out.println(topic+""+offset+""+value.toString());
    }
}
