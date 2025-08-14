package com.chatop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


/**
 * Données de création ou de mise à jour d'une location.
 */
@Getter
@Setter
public class RentalRequest {

    @NotBlank // Nom obligatoire
    private String name;

    @NotNull // Surface en m²
    private BigDecimal surface;

    @NotNull // Prix en euros
    private BigDecimal price;

    // Image obligatoire à la création, optionnelle à la mise à jour
    private MultipartFile picture;

    @NotBlank // Description textuelle
    private String description;
}
