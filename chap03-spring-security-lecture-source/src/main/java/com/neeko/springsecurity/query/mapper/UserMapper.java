package com.neeko.springsecurity.query.mapper;

import com.neeko.springsecurity.query.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface UserMapper {

    UserDTO findUserByUsername(String username);

}
