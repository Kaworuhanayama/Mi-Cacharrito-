package com.example.demo.vehicle;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VehicleDataInitializer {

    @Bean
    CommandLineRunner createInitialVehicles(VehicleRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Vehicle("MCB101", VehicleType.AUTOMOVIL, "Blanco", new BigDecimal("85000")));
                repository.save(new Vehicle("MCB202", VehicleType.CAMIONETA, "Gris", new BigDecimal("145000")));
                repository.save(new Vehicle("MCB303", VehicleType.CAMPERO, "Negro", new BigDecimal("160000")));
                repository.save(new Vehicle("MCB404", VehicleType.MICROBUS, "Azul", new BigDecimal("210000")));
                repository.save(new Vehicle("MCB505", VehicleType.MOTOCICLETA, "Rojo", new BigDecimal("55000")));
            }
        };
    }
}
