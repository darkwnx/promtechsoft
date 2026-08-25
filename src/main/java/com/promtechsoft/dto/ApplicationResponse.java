package com.promtechsoft.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private String serviceType;
    private String status;
    private LocalDateTime createdAt;
}