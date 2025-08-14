package com.chatop.controller;

import com.chatop.dto.RentalDTO;
import com.chatop.dto.RentalRequest;
import com.chatop.dto.RentalResponse;
import com.chatop.dto.RentalsResponse;
import com.chatop.model.User;
import com.chatop.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller exposant les endpoints liés aux locations.
 */
@RestController // Indique que les méthodes retournent des réponses JSON
@RequestMapping("/api/rentals") // Préfixe commun à toutes les routes
@RequiredArgsConstructor
@Tag(name = "Rentals", description = "Gestion des locations saisonnières")
@SecurityRequirement(name = "bearerAuth") // Toutes les routes nécessitent un JWT
public class RentalController {

    private final RentalService rentalService; // Service métier

    /**
     * GET /api/rentals
     *
     * @return liste complète des locations
     */
    @GetMapping
    @Operation(
        summary = "Lister toutes les locations",
        description = "Retourne la liste complète des locations disponibles",
        responses = {
            @ApiResponse(responseCode = "200", description = "Liste des locations",
                content = @Content(schema = @Schema(implementation = RentalsResponse.class)))
        }
    )
    public ResponseEntity<RentalsResponse> getAllRentals() {
        return ResponseEntity.ok(new RentalsResponse(rentalService.getAllRentals()));
    }

    /**
     * GET /api/rentals/{id}
     *
     * @param id identifiant de la location recherchée
     * @return détails de la location
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Détails d'une location",
        description = "Retourne les informations d'une location par son identifiant",
        responses = {
            @ApiResponse(responseCode = "200", description = "Location trouvée",
                content = @Content(schema = @Schema(implementation = RentalDTO.class)))
        }
    )
    public ResponseEntity<RentalDTO> getRental(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getRental(id));
    }

    /**
     * POST /api/rentals
     *
     * @param request données de la location envoyées dans un formulaire multipart
     * @return message de confirmation
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Créer une location",
        description = "Crée une nouvelle location pour l'utilisateur courant",
        responses = {
            @ApiResponse(responseCode = "200", description = "Location créée",
                content = @Content(schema = @Schema(implementation = RentalResponse.class)))
        }
    )
    public ResponseEntity<RentalResponse> createRental(@Valid @ModelAttribute RentalRequest request) {
        User owner = getCurrentUser();
        rentalService.createRental(request, owner);
        return ResponseEntity.ok(new RentalResponse("Rental created !"));
    }

    /**
     * PUT /api/rentals/{id}
     *
     * @param id      identifiant de la location à mettre à jour
     * @param request nouvelles valeurs (formulaire multipart)
     * @return message de confirmation
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Mettre à jour une location",
        description = "Met à jour une location existante appartenant à l'utilisateur",
        responses = {
            @ApiResponse(responseCode = "200", description = "Location mise à jour",
                content = @Content(schema = @Schema(implementation = RentalResponse.class)))
        }
    )
    public ResponseEntity<RentalResponse> updateRental(@PathVariable Integer id,
                                                       @Valid @ModelAttribute RentalRequest request) {
        User currentUser = getCurrentUser();
        rentalService.updateRental(id, request, currentUser);
        return ResponseEntity.ok(new RentalResponse("Rental updated !"));
    }

    /**
     * Récupère l'utilisateur authentifié à partir du contexte de sécurité.
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
