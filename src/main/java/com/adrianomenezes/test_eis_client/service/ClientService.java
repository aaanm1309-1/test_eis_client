package com.adrianomenezes.test_eis_client.service;

import com.adrianomenezes.test_eis_client.DTO.WordDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;


@Slf4j
@Service
public class ClientService {

    @Autowired
    private WebClient webClient;

    @SneakyThrows
    public void execute(String payload) {

        try {

            executeEvent(payload);

            log.info("Event finished for message: " + payload);

        }
        catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    private void executeEvent(String payload) throws URISyntaxException {
        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();

        bodyValues.add("word", payload.replaceAll("\"",""));
        String cleanPayload = payload.replaceAll("\\\"","");
        cleanPayload = cleanPayload.replaceAll("/[^a-zA-Z0-9 ]/g", "");
        Mono<WordDTO> monoReturn =
                this.webClient
                .post()
                .uri("/api/v1/words/"+cleanPayload)
                .retrieve()
                .bodyToMono(WordDTO.class);

        WordDTO wordResult = monoReturn.block();
    }

}

