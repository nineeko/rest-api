package com.neeko.springsecurity.command.service;

import com.neeko.springsecurity.command.dto.UserCreateRequest;
import com.neeko.springsecurity.command.entity.User;
import com.neeko.springsecurity.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserCreateRequest request) {
        // 중복 회원 체크 로직 등 추가 가능
        User user = modelMapper.map(request, User.class);
        user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}