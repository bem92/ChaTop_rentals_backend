package com.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Données nécessaires pour la connexion d'un utilisateur.
 */
@Getter
@Setter
public class LoginRequest {
    @Schema(example = "user@example.com")
    private String login; // Email utilisé comme identifiant

    @Schema(example = "Password123!")
    private String password; // Mot de passe fourni
}

