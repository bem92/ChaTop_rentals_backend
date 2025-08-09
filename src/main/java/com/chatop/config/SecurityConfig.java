package com.chatop.config;

import com.chatop.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indique à Spring que cette classe contient des beans de configuration
@RequiredArgsConstructor // Génère un constructeur pour injecter les dépendances finales
public class SecurityConfig {

    // Filtre personnalisé JWT qui sera appliqué à chaque requête HTTP
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean // Définit le bean de filtre de sécurité principal
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactive CSRF car on utilise JWT (pas de session côté serveur)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ) // Pas de session : chaque requête doit inclure le token
            .authorizeHttpRequests(auth -> auth
                // ✅ Routes publiques avec préfixe /api
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                // ✅ Optionnel : autoriser la documentation Swagger
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Toutes les autres nécessitent un token valide
                .anyRequest().authenticated() 
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); 
            // Ajoute notre filtre JWT AVANT celui qui gère la connexion via formulaire

        return http.build(); // Retourne la configuration de sécurité
    }

    @Bean // Fournit un encodeur de mot de passe (utilisé pour hasher les mots de passe des utilisateurs)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Fournit le gestionnaire d'authentification utilisé pour valider les identifiants
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}