package com.example.demo.vehicle;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByAvailableTrueOrderByTypeAscPlateAsc();

    List<Vehicle> findByTypeAndAvailableTrueOrderByPlateAsc(VehicleType type);

    Optional<Vehicle> findByPlate(String plate);
}
