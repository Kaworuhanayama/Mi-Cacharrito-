package com.example.demo.vehicle;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String plate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleType type;

    @Column(nullable = false, length = 40)
    private String color;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @Column(nullable = false)
    private boolean available;

    protected Vehicle() {
    }

    public Vehicle(String plate, VehicleType type, String color, BigDecimal dailyRate) {
        this.plate = plate;
        this.type = type;
        this.color = color;
        this.dailyRate = dailyRate;
        this.available = true;
    }

    public Long getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }

    public VehicleType getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
