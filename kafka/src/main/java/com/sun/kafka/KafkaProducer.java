package com.sun.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 生产者
 */
@Component
public class KafkaProducer {

    @Autowired private KafkaTemplate kafkaTemplate;

    /**
     *    发送消息
     */
    public void send(String body){
        kafkaTemplate.send("testTopic",body);
    }
}
