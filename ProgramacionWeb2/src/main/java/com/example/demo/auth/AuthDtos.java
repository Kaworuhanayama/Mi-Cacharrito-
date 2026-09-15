package com.example.demo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthDtos {
    private AuthDtos() {}

    public record RegisterRequest(
            @NotBlank String identification,
            @NotBlank String fullName,
            @NotBlank String licenseIssueDate,
            @NotBlank String licenseCategory,
            @NotBlank String licenseExpirationDate,
            @NotBlank @Email String email,
            @NotBlank String phone,
            @NotBlank @Size(min = 6) String password) {}

    public record LoginRequest(@NotBlank String identifier, @NotBlank String password) {}

    public record AuthResponse(String token, String tokenType, Long accountId,
                               String fullName, String role) {}

    public record AccountResponse(Long id, String identification, String username,
                                  String fullName, String email, String role) {
        public static AccountResponse from(Account account) {
            return new AccountResponse(account.getId(), account.getIdentification(), account.getUsername(),
                    account.getFullName(), account.getEmail(), account.getRole().name());
        }
    }
}