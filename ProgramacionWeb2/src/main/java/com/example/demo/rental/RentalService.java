package com.example.demo.rental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.auth.Account;
import com.example.demo.vehicle.Vehicle;
import com.example.demo.vehicle.VehicleRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;

    public RentalService(RentalRepository rentalRepository, VehicleRepository vehicleRepository) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Rental create(Account account, RentalDtos.CreateRentalRequest request) {
        if (request.startDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de inicio no puede ser anterior a hoy");
        }
        if (request.deliveryDate().isBefore(request.startDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de entrega no puede ser anterior al inicio");
        }

        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehículo no encontrado"));
        if (!vehicle.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El vehículo no está disponible");
        }

        long days = ChronoUnit.DAYS.between(request.startDate(), request.deliveryDate()) + 1;
        BigDecimal rentalValue = vehicle.getDailyRate().multiply(BigDecimal.valueOf(days));
        vehicle.setAvailable(false);
        return rentalRepository.save(new Rental(account, vehicle, request.startDate(), request.deliveryDate(), rentalValue));
    }

    @Transactional
    public Rental cancel(Account account, Long rentalNumber) {
        Rental rental = findRental(rentalNumber);
        if (!rental.getAccount().getId().equals(account.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El alquiler no pertenece al usuario");
        }
        if (rental.getStatus() == RentalStatus.RETURNED || rental.getStatus() == RentalStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El alquiler ya está cerrado");
        }
        rental.cancel();
        return rental;
    }

    @Transactional
    public Rental deliver(Long rentalNumber) {
        Rental rental = findRental(rentalNumber);
        if (rental.getStatus() != RentalStatus.PENDING_DELIVERY) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El alquiler no está pendiente de entrega");
        }
        rental.deliver();
        return rental;
    }

    @Transactional
    public Rental returnVehicle(Long rentalNumber, LocalDate returnDate) {
        Rental rental = findRental(rentalNumber);
        if (rental.getStatus() != RentalStatus.DELIVERED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El vehículo no está entregado");
        }
        if (returnDate.isBefore(rental.getDeliveryDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La devolución no puede ser anterior a la fecha pactada");
        }

        int lateDays = (int) ChronoUnit.DAYS.between(rental.getDeliveryDate(), returnDate);
        BigDecimal lateCharge = rental.getVehicle().getDailyRate().multiply(BigDecimal.valueOf(lateDays));
        rental.returnVehicle(returnDate, lateDays, lateCharge);
        return rental;
    }

    public Rental findRental(Long rentalNumber) {
        return rentalRepository.findById(rentalNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alquiler no encontrado"));
    }

    public List<Rental> findByAccount(Account account) {
        return rentalRepository.findByAccountIdOrderByIdDesc(account.getId());
    }

    public List<Rental> findPending() {
        return rentalRepository.findByStatusOrderByDeliveryDateAsc(RentalStatus.PENDING_DELIVERY);
    }
}
