package com.mchaum.Chatop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGES")
@Data
public class Messages {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rental_id", nullable = false)
    private Integer rentalId;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "message", nullable = false, length = 2000)
    private String message;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
