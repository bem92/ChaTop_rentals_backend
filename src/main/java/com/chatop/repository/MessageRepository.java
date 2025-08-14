package com.chatop.repository;

import com.chatop.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Référentiel JPA pour l'entité {@link Message}.
 */
@Repository // Indique à Spring que cette interface manipule la persistance des messages
public interface MessageRepository extends JpaRepository<Message, Integer> {
        // Hérite des méthodes CRUD standard de JpaRepository
}
