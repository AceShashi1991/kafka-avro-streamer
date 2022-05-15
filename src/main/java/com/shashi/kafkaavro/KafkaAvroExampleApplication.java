package com.shashi.kafkaavro;

import com.shashi.kafkaavro.service.SSDProcessor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class KafkaAvroExampleApplication implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(KafkaAvroExampleApplication.class);

    @Autowired
    @Qualifier("consumerConfig")
    private Properties kafkaConsumerConfig;
    @Autowired
    @Qualifier("streamTopology")
    private Topology stramsToplogy;



    public static void main(String[] args) {
        SpringApplication.run(KafkaAvroExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final KafkaStreams streams = new KafkaStreams(stramsToplogy, kafkaConsumerConfig);
        log.info(String.format("Topology Description %s",stramsToplogy.describe()));
        Runtime.getRuntime().addShutdownHook(new Thread("kafka-streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
            }
        });

        try {
            streams.start();
        } catch (Throwable e) {
            System.exit(1);
        }
//        System.exit(0);
    }
}
