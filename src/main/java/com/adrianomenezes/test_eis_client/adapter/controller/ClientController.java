package com.adrianomenezes.test_eis_client.adapter.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/words")
public class ClientController {

    @Autowired
    private KafkaTemplate<String, String> producerRecord;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> sendWords(@RequestParam(value = "word") String word) {

        var key = "word";
        var record = new ProducerRecord<String, String>("word_topic", key, word);
        producerRecord.send(record);

        return ResponseEntity.ok().body(word);
    }

}
