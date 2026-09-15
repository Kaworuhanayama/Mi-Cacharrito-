package com.example.demo.rental;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.Account;
import com.example.demo.rental.RentalDtos.CreateRentalRequest;
import com.example.demo.rental.RentalDtos.RentalResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponse create(@AuthenticationPrincipal Account account,
            @Valid @RequestBody CreateRentalRequest request) {
        return RentalResponse.from(rentalService.create(account, request));
    }

    @GetMapping("/mine")
    public List<RentalResponse> mine(@AuthenticationPrincipal Account account) {
        return rentalService.findByAccount(account).stream().map(RentalResponse::from).toList();
    }

    @DeleteMapping("/{rentalNumber}")
    public RentalResponse cancel(@AuthenticationPrincipal Account account, @PathVariable Long rentalNumber) {
        return RentalResponse.from(rentalService.cancel(account, rentalNumber));
    }
}
