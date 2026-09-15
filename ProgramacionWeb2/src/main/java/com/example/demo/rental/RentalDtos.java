package com.example.demo.rental;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.vehicle.VehicleType;

import jakarta.validation.constraints.NotNull;

public final class RentalDtos {

    private RentalDtos() {
    }

    public record CreateRentalRequest(@NotNull Long vehicleId, @NotNull LocalDate startDate,
            @NotNull LocalDate deliveryDate) {

    }

    public record ReturnRentalRequest(@NotNull LocalDate returnDate) {

    }

    public record RentalResponse(Long rentalNumber, String userName, String identification,
            LocalDate startDate, LocalDate deliveryDate, LocalDate returnDate,
            VehicleType vehicleType, String plate, String color,
            BigDecimal rentalValue, BigDecimal lateCharge, BigDecimal totalValue,
            int lateDays, RentalStatus status) {

        public static RentalResponse from(Rental rental) {
            return new RentalResponse(rental.getId(), rental.getAccount().getFullName(),
                    rental.getAccount().getIdentification(), rental.getStartDate(), rental.getDeliveryDate(),
                    rental.getReturnDate(), rental.getVehicle().getType(), rental.getVehicle().getPlate(),
                    rental.getVehicle().getColor(), rental.getRentalValue(), rental.getLateCharge(),
                    rental.getTotalValue(), rental.getLateDays(), rental.getStatus());
        }
    }
}
