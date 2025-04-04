package com.neeko.springsecurity.auth.service;

import com.neeko.springsecurity.auth.dto.LoginRequest;
import com.neeko.springsecurity.auth.dto.TokenResponse;
import com.neeko.springsecurity.auth.entity.RefreshToken;
import com.neeko.springsecurity.auth.repository.RefreshTokenRepository;
import com.neeko.springsecurity.command.entity.User;
import com.neeko.springsecurity.command.repository.UserRepository;
import com.neeko.springsecurity.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호");
        }

        //로그인 성공시 token 발급
        String accessToken = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername(), user.getRole().name());

        // refreshToken은 서버측에서 관리되어야 하는 데이터이고 성능 상 추천되는 환경은 Redis
        // RDBMS에 저장해서 관리하는 코드로 작성
        RefreshToken tokenEntity = RefreshToken.builder()
                .username(user.getUsername())
                .token(refreshToken)
                .expiryDate(new Date(System.currentTimeMillis()
                        + jwtTokenProvider.getRefreshExpiration())
                )
                .build();
        refreshTokenRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
