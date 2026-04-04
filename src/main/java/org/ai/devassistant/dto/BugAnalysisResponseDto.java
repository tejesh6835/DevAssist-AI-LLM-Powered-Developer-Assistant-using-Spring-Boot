package org.ai.devassistant.dto;

import lombok.Data;
import java.util.List;

@Data
public class BugAnalysisResponseDto {

    private List<Bug> bugs;
    private String summary;

    @Data
    public static class Bug {
        private String issue;
        private String severity; // HIGH / MEDIUM / LOW
    }
}