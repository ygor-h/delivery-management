package com.ygorhenrique.delivery_management.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(LocalDateTime timestamp, int status, String error, String path, List<String> messages) {
}
