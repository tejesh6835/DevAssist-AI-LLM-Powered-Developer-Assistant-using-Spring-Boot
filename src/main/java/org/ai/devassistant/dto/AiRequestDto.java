package org.ai.devassistant.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AiRequestDto {
    @NonNull
    private String type;
    private String input;
}
