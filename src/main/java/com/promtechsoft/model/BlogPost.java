package com.promtechsoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost {
    private Long id;
    private String title;
    private String content;
    private String preview;
    private String category;
    private Integer readTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean published;
}