package com.tiagogodinho.trabalhoweb.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDTO(
        @NotBlank String title,
        String description,
        @NotBlank String status
) {
}
