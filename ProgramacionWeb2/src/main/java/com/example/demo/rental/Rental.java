package com.example.demo.rental;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.auth.Account;
import com.example.demo.vehicle.Vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RentalStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rentalValue;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal lateCharge = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private int lateDays;

    protected Rental() {
    }

    public Rental(Account account, Vehicle vehicle, LocalDate startDate, LocalDate deliveryDate,
            BigDecimal rentalValue) {
        this.account = account;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.status = RentalStatus.PENDING_DELIVERY;
        this.rentalValue = rentalValue;
        this.totalValue = rentalValue;
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public BigDecimal getRentalValue() {
        return rentalValue;
    }

    public BigDecimal getLateCharge() {
        return lateCharge;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public int getLateDays() {
        return lateDays;
    }

    public void deliver() {
        this.status = RentalStatus.DELIVERED;
    }

    public void cancel() {
        this.status = RentalStatus.CANCELLED;
        this.vehicle.setAvailable(true);
    }

    public void returnVehicle(LocalDate returnDate, int lateDays, BigDecimal lateCharge) {
        this.returnDate = returnDate;
        this.lateDays = lateDays;
        this.lateCharge = lateCharge;
        this.totalValue = rentalValue.add(lateCharge);
        this.status = RentalStatus.RETURNED;
        this.vehicle.setAvailable(true);
    }
}
