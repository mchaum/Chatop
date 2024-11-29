package com.mchaum.Chatop.controller;

import com.mchaum.Chatop.model.Rental;
import com.mchaum.Chatop.repository.RentalRepository;
import com.mchaum.Chatop.service.RentalService;

import DTO.RentalUpdateDTO;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final RentalRepository rentalRepository;

    public RentalController(RentalService rentalService, RentalRepository rentalRepository) {
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
    }

    @PostMapping
    public ResponseEntity<String> createRental(@RequestHeader("Authorization") String token,
                                               @Valid @RequestBody Rental rental) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;

        Rental createdRental = rentalService.createRental(token, rental.getName(), rental.getSurface(), 
                                                          rental.getPrice(), rental.getPicture(), 
                                                          rental.getDescription());
        return ResponseEntity.ok("Rental created!");
    }
    
    @GetMapping
    public ResponseEntity<Iterable<Rental>> getAllRentals() {
        Iterable<Rental> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Rental rental = rentalService.getRentalById(id);
        if (rental != null) {
            return ResponseEntity.ok(rental);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRental(@PathVariable Long id, @Valid @RequestBody RentalUpdateDTO rentalDetails) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);

        if (rentalOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Rental rental = rentalOptional.get();

        // Mise à jour uniquement des champs non nuls //
        if (rentalDetails.getName() != null) {
            rental.setName(rentalDetails.getName());
        }
        if (rentalDetails.getSurface() != null) {
            rental.setSurface(rentalDetails.getSurface());
        }
        if (rentalDetails.getPrice() != null) {
            rental.setPrice(rentalDetails.getPrice());
        }
        if (rentalDetails.getPicture() != null) {
            rental.setPicture(rentalDetails.getPicture());
        }
        if (rentalDetails.getDescription() != null) {
            rental.setDescription(rentalDetails.getDescription());
        }

        rental.setUpdatedAt(java.time.LocalDateTime.now());
        rentalRepository.save(rental);

        return ResponseEntity.ok("Rental updated!");
    }



}
