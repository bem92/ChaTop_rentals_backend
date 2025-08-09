package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Simple DTO for returning a message after rental operations.
 */
@Getter
@AllArgsConstructor
public class RentalResponse {
    private String message;
}
