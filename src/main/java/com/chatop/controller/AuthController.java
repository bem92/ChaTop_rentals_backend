package com.chatop.controller;

import com.chatop.dto.AuthRequest;
import com.chatop.dto.AuthResponse;
import com.chatop.dto.UserMeDTO;
import com.chatop.model.User;
import com.chatop.service.JwtService;
import com.chatop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur gérant l'authentification et l'inscription des utilisateurs.
 */
@RestController
@RequestMapping("/api/auth") // ✅ Changement pour /api/auth
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * Inscription d'un nouvel utilisateur.
     *
     * @param request données d'inscription (email, nom, mot de passe)
     * @return un token JWT si l'inscription réussit
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        // ✅ Maintenant avec name selon Postman
        User user = userService.register(request.getEmail(), request.getName(), request.getPassword());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Authentifie un utilisateur et renvoie un token JWT.
     *
     * @param request données d'authentification (login/email et mot de passe)
     * @return un token JWT si la connexion réussit
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // ✅ Support "login" field selon Postman
        String emailOrLogin = request.getLogin() != null ? request.getLogin() : request.getEmail();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(emailOrLogin, request.getPassword())
        );

        User user = userService.getUserByEmail(emailOrLogin);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Renvoie les informations de l'utilisateur actuellement connecté.
     *
     * @return les informations de l'utilisateur authentifié
     */
    @GetMapping("/me")
    @Operation(summary = "Obtenir l'utilisateur connecté")
    public ResponseEntity<UserMeDTO> getCurrentUser() {
        UserMeDTO currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }
}
