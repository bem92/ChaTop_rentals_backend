package com.chatop.controller;

import com.chatop.dto.*;
import com.chatop.model.User;
import com.chatop.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller exposing rental endpoints.
 */
@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@Tag(name = "Rentals")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    @Operation(summary = "Get all rentals")
    public ResponseEntity<RentalsResponse> getAllRentals() {
        return ResponseEntity.ok(new RentalsResponse(rentalService.getAllRentals()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rental by id")
    public ResponseEntity<RentalDTO> getRental(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getRental(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create rental")
    public ResponseEntity<RentalResponse> createRental(@Valid @ModelAttribute RentalRequest request) {
        User owner = getCurrentUser();
        rentalService.createRental(request, owner);
        return ResponseEntity.ok(new RentalResponse("Rental created !"));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update rental")
    public ResponseEntity<RentalResponse> updateRental(@PathVariable Integer id,
                                                       @Valid @ModelAttribute RentalRequest request) {
        User currentUser = getCurrentUser();
        rentalService.updateRental(id, request, currentUser);
        return ResponseEntity.ok(new RentalResponse("Rental updated !"));
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
