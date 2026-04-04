package org.ai.devassistant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "purpose",
        "issues",
        "improvements",
        "bestPractices",
        "verdict"
})
public class CodeAnalysisResponseDto {

    private String purpose;
    private List<Issue> issues;

    private List<String> improvements;
    private List<String> bestPractices;

    private String verdict;
}
