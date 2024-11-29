package com.mchaum.Chatop.service;

import com.mchaum.Chatop.model.Messages;
import com.mchaum.Chatop.repository.MessageRepository;
import DTO.MessageRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Messages createAndSaveMessage(MessageRequestDTO messageDto, Integer userId) {
        Messages message = new Messages();
        message.setRentalId(messageDto.getRentalId());
        message.setMessage(messageDto.getMessage());
        message.setUserId(userId);

        return messageRepository.save(message);
    }
}
