package com.neeko.springsecurity.query.service;

import com.neeko.springsecurity.query.dto.UserDTO;
import com.neeko.springsecurity.query.dto.UserDetailResponse;
import com.neeko.springsecurity.query.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserMapper userMapper;
    public UserDetailResponse getUserDetail(String username){
        UserDTO user = Optional.ofNullable(userMapper.findUserByUsername(username))
                .orElseThrow(() -> new RuntimeException("유저 정보 찾지 못 함"));
        return UserDetailResponse.builder().user(user).build();
    }

}
