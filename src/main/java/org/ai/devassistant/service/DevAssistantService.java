package org.ai.devassistant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.devassistant.client.AiClient;
import org.ai.devassistant.dto.*;
import org.ai.devassistant.entity.AiRequestLog;
import org.ai.devassistant.repository.AiRequestLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class DevAssistantService {

    private final AiClient aiClient;
    private final AiRequestLogRepository repository;
    private final ObjectMapper objectMapper;

    public ApiResponseDto<?> explainCode(String code) {

                    String prompt = """
            You are a senior backend engineer.
            
            Return STRICT JSON only.
            
            Rules:
            - purpose: max 2 lines
            - issues: max 3 items
            - improvements: max 3 short points
            - bestPractices: max 3 short points
            - verdict: 1 line
            - No extra text
            
            Format:
            {
              "purpose": "...",
              "issues": [
                {"issue": "...", "severity": "HIGH/MEDIUM/LOW"}
              ],
              "improvements": "...",
              "bestPractices": "...",
              "verdict": "..."
            }
            
            Code:
            %s
            """.formatted(code);

        return process("CODE_EXPLAIN", code, prompt, CodeAnalysisResponseDto.class);
    }

    public ApiResponseDto<?> analyzeLogs(String logs) {

                    String prompt = """
            You are a senior backend engineer.
            
            Analyze the given logs and return STRICT JSON only.
            
            Rules:
            - rootCause: max 2 lines
            - issues: max 3 items
            - fix: max 3 short points
            - severity: HIGH/MEDIUM/LOW
            - No extra text
            - No markdown
            
            Format:
            {
              "rootCause": "...",
              "issues": [
                {"issue": "...", "severity": "HIGH/MEDIUM/LOW"}
              ],
              "fix": "...",
              "verdict": "..."
            }
            
            Logs:
            %s
            """.formatted(logs);

        return process("LOG_ANALYSIS", logs, prompt, LogAnalysisResponseDto.class);
    }

    public ApiResponseDto<?> generateTestCases(String input) {

                    String prompt = """
            You are a senior Java backend engineer.
            
            Generate minimal, production-quality JUnit 5 test cases.
            
            STRICT RULES:
            - Return ONLY valid JSON
            - No markdown
            - No explanation outside JSON
            - LIMIT to MAX 3 test cases ONLY
            - Keep code VERY SHORT
            - Use simple assertions only
            - DO NOT include unnecessary imports or comments
            - Ensure JSON is COMPLETE and CLOSED
            - Ensure all strings are properly escaped
            - Ensure class ends properly with }
            
            Format:
            {
              "testCode": "...",
              "testSummary": "..."
            }
            
            IMPORTANT:
            - If response is large, REDUCE test cases
            - NEVER truncate output
            - ALWAYS return complete JSON
            
            Code:
            %s
            """.formatted(input);

        return process("TEST_CASE_GENERATION", input, prompt, TestCaseResponseDto.class);
    }


    public ApiResponseDto<?> refactorCode(String input) {

                    String prompt = """
            You are a senior Java backend engineer.
            
            Refactor the given code.
            
            Rules:
            - Improve readability
            - Reduce complexity
            - Keep functionality same
            - Add null safety if needed
            
            STRICT RULES:
            - Return ONLY valid JSON
            - Keep response SHORT
            - Max 3 improvements
            - Each improvement: 1 line only
            - Summary: 1 short line
            - No markdown
            - No extra text
            
            Format:
            {
              "refactoredCode": "...",
              "improvements": [
                {
                  "type": "READABILITY/SAFETY/STRUCTURE",
                  "description": "short"
                }
              ],
              "summary": "short"
            }
            
            Code:
            %s
            """.formatted(input);
        return process("CODE_REFACTOR", input, prompt, CodeRefactorDto.class);

    }

    public ApiResponseDto<?> codeFix(String input) {

        String prompt = """
        You are a senior Java backend engineer.

        Fix the given code to make it production-safe.

        Rules:
        - Fix runtime issues (null checks, division by zero, etc.)
        - Improve safety without changing core logic unnecessarily
        - Keep code clean and readable

        Format:
        {
          "fixedCode": "...",
          "fixes": [
            {
              "issue": "...",
              "fix": "..."
            }
          ]
        }

        Code:
        %s
        """.formatted(input);

        return process("CODE_FIX", input, prompt, CodeFixResponseDto.class);
    }

    public ApiResponseDto<?> bugDetection(String input) {

        String prompt = """
            You are a senior Java backend engineer.

            Analyze the given code and detect bugs.

            Rules:
            - Return STRICT JSON only
            - Max 5 issues
            - Each issue must have:
              - issue
              - severity (HIGH/MEDIUM/LOW)
            - Add a short summary (1 line)
            - No explanation outside JSON
            - No markdown

            Format:
            {
              "bugs": [
                {
                  "issue": "...",
                  "severity": "HIGH"
                }
              ],
              "summary": "..."
            }

            Code:
            %s
            """.formatted(input);

        return process("BUG_DETECTION", input, prompt, BugAnalysisResponseDto.class);
    }


    private <T> ApiResponseDto<T> process(
            String type,
            String input,
            String prompt,
            Class<T> clazz
    ) {

        log.info("AI Request type: {}", type);
        log.debug("Prompt:\n{}", prompt);

        String response = aiClient.callAI(prompt);

        // Retry if broken JSON
        if (response == null || !response.trim().endsWith("}")) {
            log.warn("Incomplete AI response, retrying...");

            response = aiClient.callAI(prompt + "\nReturn COMPLETE valid JSON.");
            response = cleanJson(response);
        }

        response = cleanJson(response);

        log.debug("Raw AI Response:\n{}", response);

        try {
            T parsed = objectMapper.readValue(response, clazz);

            AiRequestLog logEntity = new AiRequestLog();
            logEntity.setType(type);
            logEntity.setInput(input);
            logEntity.setResponse(response);
            logEntity.setCreatedAt(LocalDateTime.now());
            repository.save(logEntity);

            return new ApiResponseDto<>(
                    "SUCCESS",
                    type,
                    parsed,
                    LocalDateTime.now()
            );

        } catch (Exception e) {
            log.error("JSON parsing failed", e);

            return new ApiResponseDto<>(
                    "FAILED",
                    type,
                    null, // better than raw string for consistency
                    LocalDateTime.now()
            );
        }
    }

    private String cleanJson(String response) {

        if (response == null || response.isBlank()) {
            return null;
        }

        // 1. Remove markdown wrappers (```json or ```)
        response = response.replace("```json", "")
                .replace("```", "")
                .trim();

        // 2. Handle case where entire JSON is returned as a STRING
        // Example: "{ \"key\": \"value\" }"
        if (response.startsWith("\"") && response.endsWith("\"")) {
            response = response.substring(1, response.length() - 1);

            // Unescape inner quotes
            response = response.replace("\\\"", "\"");
        }

        // 3. Remove trailing commas (common AI mistake)
        response = response.replaceAll(",\\s*}", "}");
        response = response.replaceAll(",\\s*]", "]");

        return response.trim();
    }
}
