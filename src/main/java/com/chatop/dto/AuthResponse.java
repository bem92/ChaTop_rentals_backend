package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Réponse envoyée après une authentification réussie.
 */
@Getter // Fournit un getter pour token
@AllArgsConstructor // Constructeur avec l'unique champ token
public class AuthResponse {
    /**
     * Jeton JWT à utiliser pour les requêtes suivantes.
     */
    private String token;
}
