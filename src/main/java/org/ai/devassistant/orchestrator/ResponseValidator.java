package org.ai.devassistant.orchestrator;

import lombok.extern.slf4j.Slf4j;
import org.ai.devassistant.client.AiClient;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseValidator {

    public String validateAndFix(String response, String prompt, AiClient client) {

        response = cleanJson(response);

        if (response == null || !response.trim().endsWith("}")) {
            log.warn("Incomplete AI response, retrying...");
            response = client.callAI(prompt + "\nReturn COMPLETE valid JSON.");
            response = cleanJson(response);
        }

        return response;
    }

    private String cleanJson(String response) {

        if (response == null || response.isBlank()) {
            return null;
        }

        response = response.replace("```json", "")
                .replace("```", "")
                .trim();

        if (response.startsWith("\"") && response.endsWith("\"")) {
            response = response.substring(1, response.length() - 1);
            response = response.replace("\\\"", "\"");
        }

        response = response.replaceAll(",\\s*}", "}");
        response = response.replaceAll(",\\s*]", "]");

        return response.trim();
    }
}
