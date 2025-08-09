package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Simple DTO for returning a message after sending a message.
 */
@Getter
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
