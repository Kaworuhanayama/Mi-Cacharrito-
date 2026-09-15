package com.example.demo.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByIdentification(String identification);
    Optional<Account> findByUsername(String username);
    boolean existsByIdentification(String identification);
    boolean existsByEmail(String email);
}