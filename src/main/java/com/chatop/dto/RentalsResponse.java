package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO englobant une liste de locations.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalsResponse {
    /**
     * Liste des locations retournées au client.
     */
    private List<RentalDTO> rentals;
}
