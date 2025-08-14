package com.chatop.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Service chargé de la création et de la validation des tokens JWT.
 */
@Service // Déclare cette classe comme composant Spring de type service
public class JwtService {

    @Value("${jwt.secret}") // Clé secrète injectée depuis la configuration
    private String secret;

    @Value("${jwt.expiration}") // Durée de validité du token en millisecondes
    private long expiration;

    private Key key; // Clé de signature utilisée pour signer et vérifier le token

    // Initialise la clé de signature après injection des propriétés
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Génère un token JWT à partir d'un utilisateur.
     *
     * @param userDetails informations de l'utilisateur authentifié
     * @return token signé contenant l'email de l'utilisateur
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // username = email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrait l'email (username) contenu dans le token.
     *
     * @param token jeton JWT à analyser
     * @return l'email stocké dans le sujet du token
     */
    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    /**
     * Vérifie si le token est encore valide pour l'utilisateur donné.
     *
     * @param token jeton reçu
     * @param userDetails utilisateur récupéré depuis la base
     * @return {@code true} si le token correspond à l'utilisateur et n'est pas expiré
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Vérifie si le token est expiré.
     *
     * @param token jeton à contrôler
     * @return {@code true} si la date d'expiration est passée
     */
    private boolean isTokenExpired(String token) {
        Date expirationDate = parseToken(token).getBody().getExpiration();
        return expirationDate.before(new Date());
    }

    /**
     * Décode le token JWT.
     *
     * @param token jeton à analyser
     * @return objet contenant les différentes claims du token
     */
    private Jws<Claims> parseToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
