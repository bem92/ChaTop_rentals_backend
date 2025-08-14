package com.chatop.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Représentation d'une location exposée via l'API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // Utilise snake_case pour le JSON
public class RentalDTO {
    private Integer id;              // Identifiant de la location
    private String name;             // Nom de l'annonce
    private BigDecimal surface;      // Surface en m²
    private BigDecimal price;        // Prix en euros
    private String picture;          // Chemin/URL de la photo
    private String description;      // Description détaillée
    private Integer ownerId;         // Identifiant du propriétaire
    private Timestamp createdAt;     // Date de création
    private Timestamp updatedAt;     // Date de mise à jour
}
