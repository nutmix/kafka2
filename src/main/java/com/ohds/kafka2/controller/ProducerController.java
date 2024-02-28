package com.ohds.kafka2.controller;

import com.ohds.kafka2.model.Message;
import com.ohds.kafka2.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping(path= "/produce", consumes = "application/json", produces = "application/json")
    public String produce(@RequestBody Message message)
    {
       return producerService.sendMessage(message);
    }

}
