package org.ai.devassistant.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiClient {

    private final ChatClient chatClient;

    public AiClient(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @CircuitBreaker(name = "aiService", fallbackMethod = "fallback")
    public String callAI(String prompt) {
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

    public String fallback(String prompt, Throwable ex) {
        return "{\"error\":\"AI service temporarily unavailable\"}";
    }
}
