package com.ohds.kafka2.service;

import com.ohds.kafka2.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

import java.util.Queue;

@Service
@Slf4j
public class ConsumerService {

    // This is used as a temp store of messages until the UI polls for them.
    // This is just a temp solution as it would be better to poll through...
    Queue<Message> messageQue = new LinkedList<>();

    @Value("${kafka.topic}")
    private  String TOPIC;

    // cant use TOPIC has annotation can only use compile time constants
    // @TODO change from annotations to allow topic from settings.

    @KafkaListener(id = "myConsumer", topics = "bets", groupId = "springboot-group-1", autoStartup = "false")
    public void listen (String value,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key){
        log.info(String.format("\n\n Consumed event from topic %s: key = %-10s value = %s \n\n", topic, key, value));

        Message message = new Message(key, value);
        push(message);

    } // listen

    /**
     *
     * @param message
     */
    private  synchronized void push(Message message){
        messageQue.add(message);
    }  // push

    /**
     * get next message
     * @return null if no messages or the head of the que.
     */
    public  synchronized Message pop(){

        Message message = messageQue.poll();

        if (message == null) {
            message = new Message();
        }

        return message;
    }  // pop


//    public static void consumeMessages() {
//        Properties props = new Properties();
//
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//
//        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props)) {
//            consumer.subscribe(Collections.singletonList(TOPIC));
//
//            ConsumerRecords<String, String> records = consumer.poll(1000); // poll for 1 second
//
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.printf("Received message: key = %s, value = %s%n", record.key(), record.value());
//                // You can perform any other operations with the received message here
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
