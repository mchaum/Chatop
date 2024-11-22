package com.mchaum.Chatop.controller;

import com.mchaum.Chatop.model.Rental;
import com.mchaum.Chatop.repository.RentalRepository;
import com.mchaum.Chatop.service.RentalService;
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
                                               @RequestBody Rental rental) {
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
    public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestBody Rental rentalDetails) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);

        if (rentalOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Rental rental = rentalOptional.get();
        
        // Mise à jour des informations de l'annonce //
        rental.setName(rentalDetails.getName());
        rental.setSurface(rentalDetails.getSurface());
        rental.setPrice(rentalDetails.getPrice());
        rental.setPicture(rentalDetails.getPicture());
        rental.setDescription(rentalDetails.getDescription());
        rental.setUpdatedAt(java.time.LocalDateTime.now());

        rentalRepository.save(rental); 

        return ResponseEntity.ok().body("Rental updated!");
    }

}
