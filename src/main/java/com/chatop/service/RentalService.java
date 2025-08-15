package com.chatop.service;

import com.chatop.dto.RentalDTO;
import com.chatop.dto.RentalRequest;
import com.chatop.model.Rental;
import com.chatop.model.User;
import com.chatop.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service gérant les opérations liées aux locations.
 */
@Service // Composant Spring contenant la logique métier
@RequiredArgsConstructor // Génère un constructeur pour les dépendances finales
public class RentalService {

    // Accès à la base de données pour les entités Rental
    private final RentalRepository rentalRepository;

    /**
     * Récupère toutes les locations disponibles.
     *
     * @return liste de {@link RentalDTO}
     */
    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::toDto) // Conversion Entity -> DTO
                .collect(Collectors.toList());
    }

    /**
     * Récupère une location par son identifiant.
     *
     * @param id identifiant de la location
     * @return la location correspondante sous forme de DTO
     */
    public RentalDTO getRental(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        return toDto(rental);
    }

    /**
     * Crée une nouvelle location à partir des données fournies.
     *
     * @param request informations saisies par l'utilisateur
     * @param owner   propriétaire de la location
     */
    @Transactional // Garantit une persistance atomique
    public void createRental(RentalRequest request, User owner) {
        String picturePath = storePicture(request.getPicture()); // Enregistre la photo sur le disque

        Rental rental = Rental.builder()
                .name(request.getName())
                .surface(request.getSurface())
                .price(request.getPrice())
                .picture(picturePath)
                .description(request.getDescription())
                .owner(owner)
                .build();

        rentalRepository.save(rental); // Sauvegarde dans la base
    }

    /**
     * Met à jour une location existante.
     *
     * @param id          identifiant de la location à modifier
     * @param request     nouvelles valeurs
     * @param currentUser utilisateur actuellement connecté
     */
    @Transactional
    public void updateRental(Integer id, RentalRequest request, User currentUser) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        // Vérifie que la location appartient bien à l'utilisateur
        if (!rental.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not authorized to update this rental");
        }

        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        // L'image n'est pas mise à jour selon le front-end

        rentalRepository.save(rental);
    }

    /**
     * Convertit une entité {@link Rental} en {@link RentalDTO}.
     */
    private RentalDTO toDto(Rental rental) {
        return RentalDTO.builder()
                .id(rental.getId())
                .name(rental.getName())
                .surface(rental.getSurface())
                .price(rental.getPrice())
                .picture(rental.getPicture())
                .description(rental.getDescription())
                .ownerId(rental.getOwner().getId())
                .createdAt(rental.getCreatedAt())
                .updatedAt(rental.getUpdatedAt())
                .build();
    }

    /**
     * Stocke l'image sur le disque et renvoie son chemin.
     */
    private String storePicture(MultipartFile picture) {
        if (picture == null || picture.isEmpty()) {
            return null; // Aucune image fournie
        }
        try {
            String fileName = UUID.randomUUID() + "_" + picture.getOriginalFilename();
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir); // Crée le dossier s'il n'existe pas
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Retourne une URL absolue accessible publiquement
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();
        } catch (IOException e) {
            throw new RuntimeException("Could not store picture", e);
        }
    }
}
