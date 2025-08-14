package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO minimal renvoyé après l'envoi d'un message.
 */
@Getter // Génère automatiquement les accesseurs
@AllArgsConstructor // Génère un constructeur avec l'attribut message
public class MessageResponse {
    /**
     * Contenu textuel à retourner au client.
     */
    private String message;
}
