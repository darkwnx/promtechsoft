package com.promtechsoft.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthResponse {

    private String token;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Set<String> roles;
}