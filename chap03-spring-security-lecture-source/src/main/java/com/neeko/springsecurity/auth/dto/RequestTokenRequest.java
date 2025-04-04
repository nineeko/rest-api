package com.neeko.springsecurity.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestTokenRequest {
    private final String refreshToken;
}
