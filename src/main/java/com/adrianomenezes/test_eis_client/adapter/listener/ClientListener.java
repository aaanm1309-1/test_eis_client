package com.adrianomenezes.test_eis_client.adapter.listener;


import com.adrianomenezes.test_eis_client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientListener {

    @Autowired
    ClientService service;

    @KafkaListener(containerFactory = "kafkaListenerContainerFactory", topics = "${topic.name.client}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topicName) {
        log.info("Message received from topic " + topicName + ":" + message);
        service.execute(message);
    }
}
