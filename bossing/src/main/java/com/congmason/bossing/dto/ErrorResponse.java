package com.congmason.bossing.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
