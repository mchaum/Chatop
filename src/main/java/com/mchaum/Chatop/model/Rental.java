package com.mchaum.Chatop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@Table(name = "RENTALS")
public class Rental {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties({"email", "name", "password", "createdAt", "updatedAt"})  
    // Ignore les champs non nécessaires pour ne pas les afficher dans la réponse requête //
    
    private User owner;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
