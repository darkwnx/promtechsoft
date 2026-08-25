package com.promtechsoft.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationRequest {

    @NotBlank(message = "Имя обязательно")
    private String name;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    private String phone;

    private String message;

    private String serviceType;
}