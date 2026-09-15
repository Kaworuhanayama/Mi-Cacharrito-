package com.example.demo.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.AuthDtos.AccountResponse;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @GetMapping("/me")
    public AccountResponse currentAdmin(@AuthenticationPrincipal Account account) {
        return AccountResponse.from(account);
    }
}