package com.mchaum.Chatop.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequestDTO {

    @NotNull(message = "Rental ID cannot be null")
    private Integer RentalId;

    @NotBlank(message = "Message cannot be blank")
    private String Message;
}
