package com.promtechsoft.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectRequest {
    @NotBlank(message = "Название проекта обязательно")
    private String title;

    @NotBlank(message = "Описание обязательно")
    private String description;

    private String client;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean completed = false;
}