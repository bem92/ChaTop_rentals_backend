package com.chatop.service;

import com.chatop.model.User;
import com.chatop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implémentation personnalisée de {@link UserDetailsService} utilisée par Spring Security.
 */
@Service // Déclare le bean pour l'injection
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; // Accès aux utilisateurs

    /**
     * Recherche un utilisateur par son email pour l'authentification.
     *
     * @param email adresse e-mail fournie lors du login
     * @return l'utilisateur correspondant si trouvé
     * @throws UsernameNotFoundException si aucun utilisateur ne correspond
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Conversion explicite pour respecter le type UserDetails attendu
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));
        return user;
    }
}
