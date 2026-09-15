package com.example.demo.vehicle;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.vehicle.VehicleDtos.VehicleResponse;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/available")
    public List<VehicleResponse> available(@RequestParam(required = false) VehicleType type) {
        List<Vehicle> vehicles = type == null
                ? vehicleRepository.findByAvailableTrueOrderByTypeAscPlateAsc()
                : vehicleRepository.findByTypeAndAvailableTrueOrderByPlateAsc(type);
        return vehicles.stream().map(VehicleResponse::from).toList();
    }
}
