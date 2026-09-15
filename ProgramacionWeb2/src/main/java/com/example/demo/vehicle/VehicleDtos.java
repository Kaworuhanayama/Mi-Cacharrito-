package com.example.demo.vehicle;

import java.math.BigDecimal;

public final class VehicleDtos {

    private VehicleDtos() {
    }

    public record VehicleResponse(Long id, String plate, VehicleType type, String color,
            BigDecimal dailyRate, boolean available) {

        public static VehicleResponse from(Vehicle vehicle) {
            return new VehicleResponse(vehicle.getId(), vehicle.getPlate(), vehicle.getType(),
                    vehicle.getColor(), vehicle.getDailyRate(), vehicle.isAvailable());
        }
    }
}
