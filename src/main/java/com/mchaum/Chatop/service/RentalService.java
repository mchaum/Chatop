package com.mchaum.Chatop.service;

import com.mchaum.Chatop.model.Rental;
import com.mchaum.Chatop.model.User;
import com.mchaum.Chatop.repository.RentalRepository;
import com.mchaum.Chatop.security.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public RentalService(RentalRepository rentalRepository, UserService userService, JwtUtils jwtUtils) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public Rental createRental(String token, String name, BigDecimal surface, BigDecimal price, String picture, String description) {
        Long userId = userService.findUserIdByEmail(jwtUtils.extractEmail(token)); // Récupération de l'ID de l'utilisateur connecté //
        User owner = userService.findById(userId);

        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(picture);
        rental.setDescription(description);
        rental.setOwner(owner);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);
    }
    
    public Iterable<Rental> getAllRentals() {
        return rentalRepository.findAll(); 
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElse(null); 
    }

}
