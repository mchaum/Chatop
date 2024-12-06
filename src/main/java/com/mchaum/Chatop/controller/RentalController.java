package com.mchaum.Chatop.controller;

import com.mchaum.Chatop.DTO.RentalsResponseDTO;
import com.mchaum.Chatop.model.Rental;
import com.mchaum.Chatop.repository.RentalRepository;
import com.mchaum.Chatop.service.RentalService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@SecurityRequirement(name = "bearerAuth")
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
                                               @RequestParam("name") String name,
                                               @RequestParam("surface") BigDecimal surface,
                                               @RequestParam("price") BigDecimal price,
                                               @RequestParam("description") String description,
                                               @RequestParam("file") MultipartFile file) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        
        rentalService.createRental(token, name, surface, price, description, file);

        return ResponseEntity.ok("Rental created!");
    }
    
    @GetMapping
    public ResponseEntity<RentalsResponseDTO> getAllRentals() {
        Iterable<Rental> rentalsIterable = rentalService.getAllRentals();

        List<Rental> rentals = StreamSupport
                .stream(rentalsIterable.spliterator(), false)
                .toList();

        RentalsResponseDTO response = new RentalsResponseDTO(rentals);

        return ResponseEntity.ok(response);
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
    public ResponseEntity<String> updateRental(@PathVariable Long id, 
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "surface", required = false) BigDecimal surface,
                                               @RequestParam(value = "price", required = false) BigDecimal price,
                                               @RequestParam(value = "description", required = false) String description,
                                               @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            rentalService.updateRental(id, name, surface, price, description, file);
            return ResponseEntity.ok("Rental updated!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating rental");
        }
    }




}
