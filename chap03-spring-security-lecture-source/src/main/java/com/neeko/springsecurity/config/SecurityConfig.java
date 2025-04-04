package com.neeko.springsecurity.config;
import com.neeko.springsecurity.jwt.JwtAuthenticationFilter;
import com.neeko.springsecurity.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 처리 비활성화 (default 가 활성화이므로 작성해주어야 한다)
        http.csrf(AbstractHttpConfigurer::disable)
                // 세션 로그인 x -> 토큰 로그인 설정으로 진행한다
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/api/v1/users", "/api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAuthority("USER")
                                .anyRequest().authenticated()


                );
//                        .addFilterBefore();

        return http.build();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}
