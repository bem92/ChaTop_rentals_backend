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

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entité représentant une location.
 */
@Entity
@Table(name = "RENTALS") // Nom de la table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clé primaire auto-incrémentée
    private Integer id;

    @Column(nullable = false)
    private String name; // Nom de la location

    @Column(nullable = false)
    private BigDecimal surface; // Surface en m²

    @Column(nullable = false)
    private BigDecimal price; // Prix en euros

    @Column(length = 255)
    private String picture; // Chemin/URL de la photo

    @Column(length = 2000)
    private String description; // Description de l'annonce

    @ManyToOne(optional = false) // Plusieurs locations peuvent appartenir au même propriétaire
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt; // Date de création

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt; // Date de mise à jour

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
