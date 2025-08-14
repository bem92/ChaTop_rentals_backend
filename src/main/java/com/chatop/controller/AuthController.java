package com.chatop.controller;

import com.chatop.dto.LoginRequest;
import com.chatop.dto.RegisterRequest;
import com.chatop.dto.AuthResponse;
import com.chatop.dto.UserMeDTO;
import com.chatop.model.User;
import com.chatop.service.JwtService;
import com.chatop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur gérant l'authentification et l'inscription des utilisateurs.
 */
@RestController // Composant REST retournant des réponses JSON
@RequestMapping("/api/auth") // Point d'accès racine pour l'authentification
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Gestion de l'authentification des utilisateurs")
public class AuthController {

    private final AuthenticationManager authenticationManager; // Vérifie les identifiants
    private final UserService userService; // Opérations métier sur les utilisateurs
    private final JwtService jwtService;   // Génération des tokens JWT

    /**
     * Inscription d'un nouvel utilisateur.
     *
     * @param request données d'inscription (email, nom, mot de passe)
     * @return un token JWT si l'inscription réussit
     */
    @PostMapping("/register")
    @Operation(
        summary = "Inscription d'un nouvel utilisateur",
        description = "Crée un compte utilisateur et renvoie un token JWT",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = RegisterRequest.class),
                examples = @ExampleObject(value = "{\n  \"email\": \"user@example.com\",\n  \"name\": \"John Doe\",\n  \"password\": \"Password123!\"\n}")
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Inscription réussie",
                content = @Content(schema = @Schema(implementation = AuthResponse.class)))
        }
    )
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.getEmail(), request.getName(), request.getPassword());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Authentifie un utilisateur et renvoie un token JWT.
     *
     * @param request données d'authentification (login et mot de passe)
     * @return un token JWT si la connexion réussit
     */
    @PostMapping("/login")
    @Operation(
        summary = "Connexion d'un utilisateur",
        description = "Vérifie les identifiants et renvoie un token JWT",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequest.class),
                examples = @ExampleObject(value = "{\n  \"login\": \"user@example.com\",\n  \"password\": \"Password123!\"\n}")
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie",
                content = @Content(schema = @Schema(implementation = AuthResponse.class)))
        }
    )
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );

        User user = userService.getUserByEmail(request.getLogin());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Renvoie les informations de l'utilisateur actuellement connecté.
     *
     * @return les informations de l'utilisateur authentifié
     */
    @GetMapping("/me")
    @Operation(
        summary = "Informations de l'utilisateur connecté",
        description = "Retourne les détails de l'utilisateur authentifié",
        responses = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                content = @Content(schema = @Schema(implementation = UserMeDTO.class)))
        }
    )
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserMeDTO> getCurrentUser() {
        UserMeDTO currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }
}
