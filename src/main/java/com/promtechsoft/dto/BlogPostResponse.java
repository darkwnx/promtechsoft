package com.promtechsoft.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogPostResponse {
    private Long id;
    private String title;
    private String preview;
    private String category;
    private Integer readTime;
    private LocalDateTime createdAt;
    private Boolean published;
}