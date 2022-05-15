package com.shashi.kafkaavro.service;

import com.shashi.avro.User;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.Record;

public class SSDProcessor implements Processor<String,User,Void,Void> {



    @Override
    public void process(Record<String, User> record) {

        System.out.println(record.value().getName());
        System.out.println(record.value().getFavoriteColor());
        System.out.println(record.value().getFavoriteNumber());

    }
}
