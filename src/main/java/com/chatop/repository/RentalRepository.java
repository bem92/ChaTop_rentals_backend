package com.chatop.repository;

import com.chatop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès aux données pour les entités {@link Rental}.
 */
@Repository // Composant Spring responsable des opérations CRUD sur Rental
public interface RentalRepository extends JpaRepository<Rental, Integer> {
        // Aucune méthode supplémentaire pour le moment
}
