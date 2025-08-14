package com.chatop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Entité représentant un message envoyé à propos d'une location.
 */
@Entity // Marque la classe comme persistante
@Table(name = "MESSAGES") // Spécifie la table correspondante
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clé primaire auto-incrémentée
    private Integer id;

    @ManyToOne // Plusieurs messages peuvent concerner une même location
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @ManyToOne // Plusieurs messages peuvent être envoyés par un même utilisateur
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 2000)
    private String message; // Contenu du message

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt; // Date de création

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt; // Date de dernière modification

    @PrePersist
    protected void onCreate() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
