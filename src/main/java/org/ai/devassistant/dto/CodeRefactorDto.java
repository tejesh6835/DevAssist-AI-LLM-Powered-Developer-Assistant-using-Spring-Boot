package org.ai.devassistant.dto;

import lombok.Data;
import java.util.List;

@Data
public class CodeRefactorDto {

    private String refactoredCode;
    private List<Improvement> improvements;
    private String summary;

    @Data
    public static class Improvement {
        private String type;
        private String description;
    }
}