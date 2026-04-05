package org.ai.devassistant.service;

import lombok.RequiredArgsConstructor;
import org.ai.devassistant.client.AiClient;
import org.ai.devassistant.orchestrator.ResponseValidator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiCacheService {

    private final AiClient aiClient;
    private final ResponseValidator validator;

    @Cacheable(
            value = "aiResponses",
            key = "#type + ':' + #input.hashCode()"
    )
    public String getAiResponse(String type, String input, String prompt) {

        String response = aiClient.callAI(prompt);

        return validator.validateAndFix(response, prompt, aiClient);
    }
}