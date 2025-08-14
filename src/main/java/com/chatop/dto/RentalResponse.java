package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO de confirmation utilisé après les opérations sur les locations.
 */
@Getter
@AllArgsConstructor
public class RentalResponse {
    /**
     * Message à afficher côté client.
     */
    private String message;
}
