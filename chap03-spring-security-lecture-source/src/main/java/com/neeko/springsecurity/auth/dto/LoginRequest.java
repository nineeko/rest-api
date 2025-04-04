package com.neeko.springsecurity.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@RequiredArgsConstructor
public class LoginRequest {
    private final String username;
    private final String password;
}
