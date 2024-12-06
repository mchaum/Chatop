package com.mchaum.Chatop.DTO;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RentalUpdateDTO {

    private String name;
    private BigDecimal surface;
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private BigDecimal price;
    private String picture;
    private String description;
}
