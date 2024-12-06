package com.mchaum.Chatop.service;

import com.mchaum.Chatop.model.Rental;
import com.mchaum.Chatop.model.User;
import com.mchaum.Chatop.repository.RentalRepository;
import com.mchaum.Chatop.security.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final UploadService uploadService;

    public RentalService(RentalRepository rentalRepository, UserService userService, JwtUtils jwtUtils, UploadService uploadService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.uploadService = uploadService; 
    }

    @Transactional
    public Rental createRental(String token, String name, BigDecimal surface, BigDecimal price, String description, MultipartFile file) {
        Long userId = userService.findUserIdByEmail(jwtUtils.extractEmail(token)); 
        User owner = userService.findById(userId);
        
        String pictureUrl = null;
        try {
            pictureUrl = uploadService.uploadImage(file);  
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image", e);
        }

        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(pictureUrl);  
        rental.setDescription(description);
        rental.setOwner(owner);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);
    }
    
    @Transactional
    public Rental updateRental(Long rentalId, String name, BigDecimal surface, BigDecimal price, String description, MultipartFile file) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() -> new RuntimeException("Rental not found"));

        if (name != null) rental.setName(name);
        if (surface != null) rental.setSurface(surface);
        if (price != null) rental.setPrice(price);
        if (description != null) rental.setDescription(description);

        if (file != null && !file.isEmpty()) {
            String pictureUrl;
            try {
                pictureUrl = uploadService.uploadImage(file);  
                rental.setPicture(pictureUrl);  
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image", e);
            }
        }

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
