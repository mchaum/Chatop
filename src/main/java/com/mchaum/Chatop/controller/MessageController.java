package com.mchaum.Chatop.controller;

import com.mchaum.Chatop.service.MessageService;
import com.mchaum.Chatop.service.UserService;

import DTO.MessageRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/messages")
public class MessageController {
    
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String sendMessage(@Valid @RequestBody MessageRequestDTO messageDto) {
        if (messageDto.getMessage() == null || messageDto.getMessage().isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = null;

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            userId = userService.findUserIdByEmail(userDetails.getUsername()).intValue();
        }

        messageService.createAndSaveMessage(messageDto, userId);

        return "Message sent successfully";
    }

}

