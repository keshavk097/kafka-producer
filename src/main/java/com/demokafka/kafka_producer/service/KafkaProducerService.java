package com.demokafka.kafka_producer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class KafkaProducerService {
    private final WebClient webClient;

    @Value("${kafka.bridge.url}")
    private String kafkaBridgeUrl;

    @Value("${kafka.topic}")
    private String topic;

    public KafkaProducerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void sendMessage(String message) {
        String url = kafkaBridgeUrl + "/topics/" + topic;

        webClient.post()
                .uri(url)
                .bodyValue(Map.of(
                        "key", "my-key",
                        "value", message
                ))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("Message Sent: " + response));
    }
}

