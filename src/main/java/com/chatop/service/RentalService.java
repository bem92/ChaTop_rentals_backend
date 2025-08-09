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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service handling rental operations.
 */
@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;

    /**
     * Retrieve all rentals.
     */
    public List<RentalDTO> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a rental by its id.
     */
    public RentalDTO getRental(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        return toDto(rental);
    }

    /**
     * Create a new rental.
     */
    @Transactional
    public void createRental(RentalRequest request, User owner) {
        String picturePath = storePicture(request.getPicture());

        Rental rental = Rental.builder()
                .name(request.getName())
                .surface(request.getSurface())
                .price(request.getPrice())
                .picture(picturePath)
                .description(request.getDescription())
                .owner(owner)
                .build();

        rentalRepository.save(rental);
    }

    /**
     * Update an existing rental.
     */
    @Transactional
    public void updateRental(Integer id, RentalRequest request, User currentUser) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        if (!rental.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not authorized to update this rental");
        }

        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        // Picture is not updated according to front-end

        rentalRepository.save(rental);
    }

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

    private String storePicture(MultipartFile picture) {
        if (picture == null || picture.isEmpty()) {
            return null;
        }
        try {
            String fileName = UUID.randomUUID() + "_" + picture.getOriginalFilename();
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not store picture", e);
        }
    }
}
