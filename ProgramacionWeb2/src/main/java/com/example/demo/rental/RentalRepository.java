package com.example.demo.rental;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByAccountIdOrderByIdDesc(Long accountId);

    List<Rental> findByStatusOrderByDeliveryDateAsc(RentalStatus status);
}
