package com.promtechsoft.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BlogPostRequest {

    @NotBlank(message = "Заголовок обязателен")
    @Size(min = 3, max = 200, message = "Заголовок должен быть от 3 до 200 символов")
    private String title;

    @NotBlank(message = "Содержание обязательно")
    private String content;

    @Size(max = 500, message = "Превью не должно превышать 500 символов")
    private String preview;

    private String category;
    private Integer readTime;
    private Boolean published = false;
}