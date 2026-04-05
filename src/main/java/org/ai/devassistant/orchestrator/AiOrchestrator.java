package org.ai.devassistant.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.devassistant.client.AiClient;
import org.ai.devassistant.dto.ApiResponseDto;
import org.ai.devassistant.entity.AiRequestLog;
import org.ai.devassistant.repository.AiRequestLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiOrchestrator {

    private final AiClient aiClient;
    private final ObjectMapper objectMapper;
    private final AiRequestLogRepository repository;
    private final ResponseValidator validator;

    public <T> ApiResponseDto<T> execute(
            String type,
            String input,
            String prompt,
            Class<T> clazz
    ) {

        log.info("AI Request type: {}", type);

        String response = aiClient.callAI(prompt);

        response = validator.validateAndFix(response, prompt, aiClient);

        try {
            T parsed = objectMapper.readValue(response, clazz);

            saveInDB(type, input, response);

            return new ApiResponseDto<>("SUCCESS", type, parsed, LocalDateTime.now());

        } catch (Exception e) {
            log.error("JSON parsing failed", e);
            return new ApiResponseDto<>("FAILED", type, null, LocalDateTime.now());
        }
    }

    private void saveInDB(String type, String input, String response) {
        AiRequestLog logEntity = new AiRequestLog();
        logEntity.setType(type);
        logEntity.setInput(input);
        logEntity.setResponse(response);
        logEntity.setCreatedAt(LocalDateTime.now());
        repository.save(logEntity);
    }
}