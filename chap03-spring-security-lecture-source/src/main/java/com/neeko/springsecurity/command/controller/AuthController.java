package com.neeko.springsecurity.command.controller;


import com.neeko.springsecurity.auth.dto.LoginRequest;
import com.neeko.springsecurity.auth.dto.TokenResponse;
import com.neeko.springsecurity.auth.service.AuthService;
import com.neeko.springsecurity.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        TokenResponse token = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(token));
    }
}
