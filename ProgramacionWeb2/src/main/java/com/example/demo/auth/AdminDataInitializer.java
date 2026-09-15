package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminDataInitializer {
    @Bean
    CommandLineRunner createInitialAdmin(AccountRepository repository, PasswordEncoder passwordEncoder,
                                         @Value("${app.admin.username}") String username,
                                         @Value("${app.admin.password}") String password,
                                         @Value("${app.admin.name}") String name) {
        return args -> {
            if (repository.findByUsername(username).isEmpty()) {
                repository.save(new Account(null, username, name, passwordEncoder.encode(password), Role.ADMIN,
                        null, null, null, null, null));
            }
        };
    }
}