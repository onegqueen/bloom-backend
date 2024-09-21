package com.example.bloombackend.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.List;

@Component
public class AIUtil {

    private final RestTemplate restTemplate;
    private final String apiUrl = "https://api.anthropic.com/v1/messages";

    @Value("${ai.api.key}")
    private String apiKey;

    public AIUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateCompletion(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
            "model", "claude-3-sonnet-20240229",
            "max_tokens", 300,
            "messages", List.of(Map.of(
                "role", "user",
                "content", prompt
            ))
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
        List<Map<String, Object>> content = (List<Map<String, Object>>) response.getBody().get("content");
        return (String) content.get(0).get("text");
    }
}