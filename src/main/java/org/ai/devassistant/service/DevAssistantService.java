package org.ai.devassistant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ai.devassistant.dto.*;
import org.ai.devassistant.orchestrator.AiOrchestrator;
import org.ai.devassistant.orchestrator.PromptBuilder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DevAssistantService {

    private final AiOrchestrator orchestrator;
    private final PromptBuilder promptBuilder;

    public ApiResponseDto<?> explainCode(String input) {

        String prompt = promptBuilder.build("CODE_EXPLAIN", input);

        return orchestrator.execute(
                "CODE_EXPLAIN",
                input,
                prompt,
                CodeAnalysisResponseDto.class
        );
    }

    public ApiResponseDto<?> analyzeLogs(String input) {

        String prompt = promptBuilder.build("LOG_ANALYSIS", input);

        return orchestrator.execute(
                "LOG_ANALYSIS",
                input,
                prompt,
                LogAnalysisResponseDto.class
        );
    }

    public ApiResponseDto<?> generateTestCases(String input) {

        String prompt = promptBuilder.build("TEST_CASE_GENERATION", input);

        return orchestrator.execute(
                "TEST_CASE_GENERATION",
                input,
                prompt,
                TestCaseResponseDto.class
        );
    }


    public ApiResponseDto<?> refactorCode(String input) {

        String prompt = promptBuilder.build("CODE_REFACTOR", input);

        return orchestrator.execute(
                "CODE_REFACTOR",
                input,
                prompt,
                CodeRefactorDto.class
        );

    }

    public ApiResponseDto<?> codeFix(String input) {

        String prompt = promptBuilder.build("CODE_FIX", input);

        return orchestrator.execute(
                "CODE_FIX",
                input,
                prompt,
                CodeFixResponseDto.class
        );
    }

    public ApiResponseDto<?> bugDetection(String input) {

        String prompt = promptBuilder.build("BUG_DETECTION", input);

        return orchestrator.execute(
                "BUG_DETECTION",
                input,
                prompt,
                BugAnalysisResponseDto.class
        );

    }

}
