package org.ai.devassistant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponseDto<T> {
    private String status;
    private String type;
    private T data;
    private LocalDateTime timestamp;
}
