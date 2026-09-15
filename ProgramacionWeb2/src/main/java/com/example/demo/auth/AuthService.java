package com.example.demo.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.auth.AuthDtos.AccountResponse;
import com.example.demo.auth.AuthDtos.AuthResponse;
import com.example.demo.auth.AuthDtos.LoginRequest;
import com.example.demo.auth.AuthDtos.RegisterRequest;

@Service
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AccountResponse register(RegisterRequest request) {
        if (accountRepository.existsByIdentification(request.identification())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La identificación ya está registrada");
        }
        if (accountRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        Account account = new Account(request.identification(), null, request.fullName(),
                passwordEncoder.encode(request.password()), Role.USER, request.licenseIssueDate(),
                request.licenseCategory(), request.licenseExpirationDate(), request.email(), request.phone());
        return AccountResponse.from(accountRepository.save(account));
    }

    public AuthResponse login(LoginRequest request) {
        Account account = accountRepository.findByIdentification(request.identifier())
                .or(() -> accountRepository.findByUsername(request.identifier()))
                .filter(candidate -> passwordEncoder.matches(request.password(), candidate.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        return new AuthResponse(jwtService.createToken(account), "Bearer", account.getId(),
                account.getFullName(), account.getRole().name());
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cuenta no encontrada"));
    }
}