package com.promtechsoft.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceRequest {
    @NotBlank(message = "Название услуги обязательно")
    private String title;

    @NotBlank(message = "Описание обязательно")
    private String description;

    private BigDecimal price;
    private Boolean active = true;
}