package com.example.demo.rental;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rental.RentalDtos.RentalResponse;
import com.example.demo.rental.RentalDtos.ReturnRentalRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/rentals")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRentalController {

    private final RentalService rentalService;

    public AdminRentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/pending")
    public List<RentalResponse> pending() {
        return rentalService.findPending().stream().map(RentalResponse::from).toList();
    }

    @PatchMapping("/{rentalNumber}/deliver")
    public RentalResponse deliver(@PathVariable Long rentalNumber) {
        return RentalResponse.from(rentalService.deliver(rentalNumber));
    }

    @PatchMapping("/{rentalNumber}/return")
    public RentalResponse returnVehicle(@PathVariable Long rentalNumber,
            @Valid @RequestBody ReturnRentalRequest request) {
        return RentalResponse.from(rentalService.returnVehicle(rentalNumber, request.returnDate()));
    }
}
