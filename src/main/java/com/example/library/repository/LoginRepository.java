package com.example.library.repository;

import com.example.library.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, String> {
    Login findLoginBySessionId(String sessionId);
}
