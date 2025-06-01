package com.example.movieApi.auth.repositories;

import com.example.movieApi.auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
}
