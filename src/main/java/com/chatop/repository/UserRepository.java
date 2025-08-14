package com.chatop.repository;

import com.chatop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interface d'accès aux données pour l'entité {@link User}.
 */
@Repository // Gestion des opérations CRUD sur les utilisateurs
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Recherche un utilisateur à partir de son adresse e-mail.
     *
     * @param email adresse e-mail recherchée
     * @return un Optional contenant l'utilisateur s'il existe
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie l'existence d'un utilisateur avec cette adresse e-mail.
     *
     * @param email adresse e-mail à tester
     * @return {@code true} si un utilisateur est trouvé
     */
    boolean existsByEmail(String email);
}
