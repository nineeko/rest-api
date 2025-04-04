package com.neeko.springsecurity.auth.repository;

import com.neeko.springsecurity.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

}
