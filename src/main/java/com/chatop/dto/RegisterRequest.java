package com.chatop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Données reçues lors de l'inscription d'un nouvel utilisateur.
 */
@Getter
@Setter
public class RegisterRequest {
    @Schema(example = "user@example.com")
    private String email; // Adresse e-mail de connexion

    @Schema(example = "John Doe")
    private String name; // Nom complet affiché

    @Schema(example = "Password123!")
    private String password; // Mot de passe en clair à chiffrer
}

