package org.ai.devassistant.dto;

import lombok.Data;

import java.util.List;

@Data
public class CodeFixResponseDto {

    private String fixedCode;
    private List<FixItem> fixes;

    @Data
    public static class FixItem {
        private String issue;
        private String fix;
    }
}
