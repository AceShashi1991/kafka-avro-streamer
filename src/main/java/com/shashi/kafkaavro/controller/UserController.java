package com.shashi.kafkaavro.controller;

import com.shashi.avro.User;
import com.shashi.kafkaavro.config.KafkaProducerConfig;
import com.shashi.kafkaavro.service.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private Producer producer;

    UserController(Producer producer){
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("name") String name,@RequestParam("favNum") Integer favoriteNumber,@RequestParam("favCol") String favoriteColour){
        this.producer.sendUserToKafkaTopic(User.newBuilder()
                .setName(name)
                .setFavoriteColor(favoriteColour)
                .setFavoriteNumber(favoriteNumber).build());
    }
}
