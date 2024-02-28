package com.ohds.kafka2.service;

import com.ohds.kafka2.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String TOPIC;

    public String sendMessage(Message message) {

        String key = message.getKey();
        String value = message.getValue();

        try {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, key, value);
            SendResult<String, String> result = future.join();
            return result.toString();
        } catch (CompletionException cex) {
            log.error("failed to send:", cex);
            return "failed";
        }


        /*** this was the async version
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info(String.format("\n\n Produced event to topic %s: key = %-10s value = %s \n\n", TOPIC, key, value));
                return ("Message Sent");
            } else {
                log.error("Unable to produce event to topic " + TOPIC + ": key = " + key + " value = " + value);
                log.error("prodcuer exception:", ex);
            }
        });
         ***********/

    } // sendMessage

} // ProducerService
