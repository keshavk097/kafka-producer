package com.demokafka.kafka_producer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import java.util.*;

@Service
public class KafkaProducerService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String kafkaBridgeUrl = "http://34.44.128.141/topics/my-topic"; // Use the Kafka Bridge External IP

    public String sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.kafka.json.v2+json");

        Map<String, Object> payload = new HashMap<>();
        payload.put("records", Collections.singletonList(Collections.singletonMap("value", message)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(kafkaBridgeUrl, request, String.class);

        return response.getBody();
    }
}


