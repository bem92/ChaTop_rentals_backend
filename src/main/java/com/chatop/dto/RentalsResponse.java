package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO wrapping list of rentals.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalsResponse {
    private List<RentalDTO> rentals;
}
