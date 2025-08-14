package com.chatop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Objet polyvalent utilisé pour les opérations d'authentification
 * (inscription ou connexion).
 */
@Getter @Setter
public class AuthRequest {
    private String email;   // Utilisé lors de l'inscription
    private String name;    // Nom complet pour l'inscription
    private String login;   // Email utilisé lors de la connexion
    private String password; // Mot de passe fourni
}