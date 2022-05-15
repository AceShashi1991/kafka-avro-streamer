package com.shashi.kafkaavro.service;

import com.shashi.avro.User;
import com.shashi.kafkaavro.config.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.To;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class Consumer {


    @Autowired
    private KafkaConsumerConfig consumerConfig;


    @Bean("streamTopology")
    public Topology createTopology() {
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, User> userKStream = builder.stream(consumerConfig.getTopicName());
        userKStream.process(()->new SSDProcessor());
        return builder.build();
    }
}
