package com.mchaum.Chatop.controller;

import com.mchaum.Chatop.model.MessageRequestDTO;
import com.mchaum.Chatop.model.Messages;
import com.mchaum.Chatop.repository.MessageRepository;
import com.mchaum.Chatop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @PostMapping
    public String sendMessage(@Valid @RequestBody MessageRequestDTO messageDto) {
        // Log the incoming message
        if (messageDto.getMessage() == null || messageDto.getMessage().isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        // Récupération de l'utilisateur connecté
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = null;

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            userId = userService.findUserIdByEmail(userDetails.getUsername()).intValue();
        }

        // Création de l'entité Messages à partir du DTO
        Messages message = new Messages();
        message.setRentalId(messageDto.getRentalId());
        message.setMessage(messageDto.getMessage());  // Ensure this is not null or empty
        message.setUserId(userId);

        // Enregistrement dans la base de données
        messageRepository.save(message);

        return "Message sent successfully";
    }

}

