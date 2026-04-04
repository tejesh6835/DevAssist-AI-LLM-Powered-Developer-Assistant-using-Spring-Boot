package org.ai.devassistant.controller;

import lombok.RequiredArgsConstructor;
import org.ai.devassistant.dto.AiRequestDto;
import org.ai.devassistant.dto.ApiResponseDto;
import org.ai.devassistant.service.DevAssistantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class DevAssistantController {

    private final DevAssistantService service;
    @PostMapping("/ask")
    public ApiResponseDto<?> generate(@RequestBody AiRequestDto dto) {
        String type= dto.getType();
        return switch (type) {
            case "explain" -> service.explainCode(dto.getInput());
            case "logs" -> service.analyzeLogs(dto.getInput());
            case "testcases" -> service.generateTestCases(dto.getInput());
            case "refactor" -> service.refactorCode(dto.getInput());
            case "detect-bugs" -> service.bugDetection(dto.getInput());
            case "code-fix" -> service.codeFix(dto.getInput());
            default ->
                    new ApiResponseDto<>("error", "unknown_type", Map.of("message", "Unknown request type: " + type), null);
        };

    }

}
