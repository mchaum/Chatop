package com.mchaum.Chatop.repository;

import com.mchaum.Chatop.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Messages, Integer> {
}
