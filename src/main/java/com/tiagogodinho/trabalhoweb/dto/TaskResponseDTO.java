package com.tiagogodinho.trabalhoweb.dto;

import com.tiagogodinho.trabalhoweb.model.Task;

public record TaskResponseDTO(Long id, String title, String description, String status) {
    public TaskResponseDTO(Task task) {
        this(task.getId(), task.getTitle(), task.getDescription(), task.getStatus());
    }
}
