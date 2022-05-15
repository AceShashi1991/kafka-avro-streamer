package com.shashi.kafkaavro.config;

import com.shashi.avro.User;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProducerConfig {
    private String bootstrapServers;
    private String schemaRegistryUrl;
    private Integer topicPartitions;
    private short topicReplicationFactor;
    private String topicName;

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


    @Bean
    public Map<String, Object> producerConfig() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "AvroProducer");

        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

        return props;
    }

    @Bean
    public ProducerFactory<String, User> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean("producerTemplate")
    public KafkaTemplate<String, User> kafkaTemplate(ProducerFactory<String, User> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
