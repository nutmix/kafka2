package com.ohds.kafka2.controller;

import com.ohds.kafka2.model.Message;
import com.ohds.kafka2.service.ConsumerService;
import com.ohds.kafka2.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/consume")
    public Message consume() {

        // pop returns "empty" message if no message. I.e. key and value not set
        Message message = consumerService.pop();

        return message;
    }


}
