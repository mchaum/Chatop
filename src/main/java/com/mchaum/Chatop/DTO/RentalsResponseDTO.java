package com.mchaum.Chatop.DTO;

import com.mchaum.Chatop.model.Rental;
import lombok.Data;

import java.util.List;

@Data
public class RentalsResponseDTO {
    private List<Rental> RENTALS;

    public RentalsResponseDTO(List<Rental> rentals) {
        this.RENTALS = rentals;
    }
}
