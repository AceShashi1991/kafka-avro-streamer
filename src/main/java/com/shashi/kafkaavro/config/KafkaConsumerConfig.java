package com.shashi.kafkaavro.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConsumerConfig {

    private String bootstrapServers;
    private String schemaRegistryUrl;
    private Integer topicPartitions;
    private short topicReplicationFactor;
    private String topicName;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    public void setSchemaRegistryUrl(String schemaRegistryUrl) {
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    public Integer getTopicPartitions() {
        return topicPartitions;
    }

    public void setTopicPartitions(Integer topicPartitions) {
        this.topicPartitions = topicPartitions;
    }

    public short getTopicReplicationFactor() {
        return topicReplicationFactor;
    }

    public void setTopicReplicationFactor(short topicReplicationFactor) {
        this.topicReplicationFactor = topicReplicationFactor;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Bean("consumerConfig")
    public Properties consumerConfig() {
        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "AvroConsumer");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "UserAvroConsumer");

        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class.getName());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

        props.put(StreamsConfig.POLL_MS_CONFIG,3000);
        props.put(StreamsConfig.MAX_TASK_IDLE_MS_CONFIG,3000);
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG,2);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        return props;
    }




}
