package com.chatop.service;

import com.chatop.dto.UserMeDTO;
import com.chatop.exception.UserAlreadyExistsException;
import com.chatop.exception.UserNotFoundException;
import com.chatop.model.User;
import com.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service chargé de la gestion des utilisateurs.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Inscrit un nouvel utilisateur dans la base de données.
     *
     * @param email    adresse e-mail de l'utilisateur
     * @param name     nom complet de l'utilisateur
     * @param password mot de passe en clair qui sera encodé
     * @return l'utilisateur nouvellement créé
     * @throws UserAlreadyExistsException si l'adresse e-mail est déjà utilisée
     */
    @Transactional
    public User register(String email, String name, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email déjà utilisé");
        }

        User user = User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();

        return userRepository.save(user);
    }

    /**
     * Récupère un utilisateur à partir de son adresse e-mail.
     *
     * @param email adresse e-mail recherchée
     * @return l'utilisateur correspondant
     * @throws UserNotFoundException si aucun utilisateur n'est trouvé
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable avec cet email"));
    }

    /**
     * Récupère l'utilisateur actuellement authentifié.
     *
     * @return les informations de l'utilisateur connecté
     * @throws UserNotFoundException si aucun utilisateur n'est authentifié
     */
    public UserMeDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new UserNotFoundException("Utilisateur non authentifié");
        }

        User user = (User) authentication.getPrincipal();
        return new UserMeDTO(user.getId(), user.getName(), user.getEmail());
    }
}
