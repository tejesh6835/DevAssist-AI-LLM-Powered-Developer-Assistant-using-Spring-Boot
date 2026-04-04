package org.ai.devassistant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "rootCause",
        "issues",
        "fix",
        "verdict"
})
public class LogAnalysisResponseDto {

    private String rootCause;
    private List<Issue> issues;
    private String fix;
    private String verdict;
}