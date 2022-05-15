package com.shashi.kafkaavro.service;

import com.shashi.avro.User;
import com.shashi.kafkaavro.config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final KafkaTemplate<String, User> kafkaTemplate;
    private final KafkaProducerConfig producerConfigProps;

    Producer(@Qualifier("producerTemplate") KafkaTemplate<String, User> producer, KafkaProducerConfig producerConfigProps){
        this.kafkaTemplate = producer;
        this.producerConfigProps = producerConfigProps;
    }

    public void sendUserToKafkaTopic(User user){
        this.kafkaTemplate.send(new ProducerRecord<>(producerConfigProps.getTopicName()
                ,user.getName()
                ,user));

    }
}
