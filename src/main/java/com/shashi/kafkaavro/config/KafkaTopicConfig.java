package com.shashi.kafkaavro.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    private KafkaProducerConfig kafkaProducerConfig;

    @Autowired
    public KafkaTopicConfig(KafkaProducerConfig kafkaProducerConfig) {
        this.kafkaProducerConfig = kafkaProducerConfig;
    }

    @Bean
    public NewTopic addBookTopic() {
        return TopicBuilder.name(kafkaProducerConfig.getTopicName())
                .partitions(kafkaProducerConfig.getTopicPartitions())
                .replicas(kafkaProducerConfig.getTopicReplicationFactor()).build();
    }
}
