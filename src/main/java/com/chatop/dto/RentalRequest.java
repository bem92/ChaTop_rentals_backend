package com.chatop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


/**
 * DTO for rental creation or update requests.
 */
@Getter
@Setter
public class RentalRequest {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal surface;

    @NotNull
    private BigDecimal price;

    // Picture is required for creation, optional for update
    private MultipartFile picture;

    @NotBlank
    private String description;
}
