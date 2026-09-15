package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.auth.Account;
import com.example.demo.auth.AccountRepository;
import com.example.demo.auth.Role;
import com.example.demo.rental.Rental;
import com.example.demo.rental.RentalDtos.CreateRentalRequest;
import com.example.demo.rental.RentalService;
import com.example.demo.rental.RentalStatus;
import com.example.demo.vehicle.Vehicle;
import com.example.demo.vehicle.VehicleRepository;

@SpringBootTest
class RentalServiceTests {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private com.example.demo.rental.RentalRepository rentalRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        String testId = String.valueOf(System.nanoTime());
        account = accountRepository.save(new Account(testId, "Usuario" + testId, "Usuario Prueba",
                "password", Role.USER, "2020-01-01", "B", "2030-01-01",
                "usuario" + testId + "@prueba.com", "3000000000"));
    }

    @Test
    void createsDeliversAndReturnsRentalWithLateCharge() {
        Vehicle vehicle = vehicleRepository.findByPlate("MCB101").orElseThrow();
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate deliveryDate = startDate.plusDays(1);

        Rental rental = rentalService.create(account, new CreateRentalRequest(vehicle.getId(), startDate, deliveryDate));

        assertThat(rental.getStatus()).isEqualTo(RentalStatus.PENDING_DELIVERY);
        assertThat(vehicleRepository.findById(vehicle.getId()).orElseThrow().isAvailable()).isFalse();
        assertThat(rental.getRentalValue()).isEqualByComparingTo(vehicle.getDailyRate().multiply(java.math.BigDecimal.valueOf(2)));

        rentalService.deliver(rental.getId());
        Rental returnedRental = rentalService.returnVehicle(rental.getId(), deliveryDate.plusDays(2));

        assertThat(returnedRental.getStatus()).isEqualTo(RentalStatus.RETURNED);
        assertThat(returnedRental.getLateDays()).isEqualTo(2);
        assertThat(returnedRental.getLateCharge()).isEqualByComparingTo(vehicle.getDailyRate().multiply(java.math.BigDecimal.valueOf(2)));
        assertThat(vehicleRepository.findById(vehicle.getId()).orElseThrow().isAvailable()).isTrue();
    }

    @Test
    void cancellationMakesVehicleAvailableAgain() {
        Vehicle vehicle = vehicleRepository.findByPlate("MCB202").orElseThrow();
        LocalDate startDate = LocalDate.now().plusDays(1);
        Rental rental = rentalService.create(account, new CreateRentalRequest(vehicle.getId(), startDate, startDate));

        rentalService.cancel(account, rental.getId());

        assertThat(rentalRepository.findById(rental.getId()).orElseThrow().getStatus())
                .isEqualTo(RentalStatus.CANCELLED);
        assertThat(vehicleRepository.findById(vehicle.getId()).orElseThrow().isAvailable()).isTrue();
    }
}
