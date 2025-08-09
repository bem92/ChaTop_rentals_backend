package com.chatop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO représentant les informations essentielles de l'utilisateur connecté.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMeDTO {
    private Integer id;
    private String name;
    private String email;
}
