package org.ai.devassistant.orchestrator;

import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String build(String type, String input) {

        String codeExplain = """
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
            """;

        String analyzeLogs = """
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
            """;

        String generateTestCases = """
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
            """;

        String codeRefactor = """
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
            """;

        String codeFix = """
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
        """;

        String bugDetection = """
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
            """;

        return switch (type) {

            case "CODE_EXPLAIN" -> codeExplain.formatted(input);
            case "LOG_ANALYSIS" -> analyzeLogs.formatted(input);
            case "TEST_CASE_GENERATION" -> generateTestCases.formatted(input);
            case "CODE_REFACTOR" -> codeRefactor.formatted(input);
            case "CODE_FIX" -> codeFix.formatted(input);
            case "BUG_DETECTION" -> bugDetection.formatted(input);

            default -> throw new IllegalArgumentException("Unknown type");
        };
    }
}
