package com.chatop.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Requête envoyée pour poster un message concernant une location.
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // Convertit les noms en snake_case
public class MessageRequest {

    @NotNull // Identifiant de la location ciblée
    private Integer rentalId;

    @NotBlank // Contenu du message rédigé par l'utilisateur
    private String message;
}
