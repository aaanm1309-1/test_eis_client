package com.adrianomenezes.test_eis_client.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class WordDTO {

    private UUID uuid;
    private String word;

    public WordDTO() {}

    public WordDTO(String word) {
        this.uuid  = UUID.randomUUID();
        this.word = word;

    }

}