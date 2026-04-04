package org.ai.devassistant.client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiClient {

    private final ChatClient chatClient;

    public AiClient(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String callAI(String prompt) {
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
