package com.chatop.security;

import com.chatop.service.JwtService;
import com.chatop.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre exécuté à chaque requête pour valider le token JWT et
 * authentifier l'utilisateur.
 */
@Component // Enregistrement automatique du filtre dans le contexte Spring
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Service de gestion des JWT
    private final UserDetailsServiceImpl userDetailsService; // Récupération des utilisateurs

    /**
     * Exclut certaines routes du filtrage JWT.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/api/auth/login") ||
               path.equals("/api/auth/register") ||
               path.startsWith("/swagger-ui/") ||
               path.startsWith("/v3/api-docs/");
    }

    /**
     * Vérifie la présence d'un token, le valide et, si correct, insère
     * l'utilisateur dans le {@link SecurityContextHolder}.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;

        // Vérifie la présence du header Authorization + format Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Enlève "Bearer" et récupère uniquement le token, même si d'autres valeurs sont présentes
        token = authHeader.substring(7).split(",")[0].trim();
        try {
            userEmail = jwtService.extractUsername(token);
        } catch (JwtException e) {
            // Token invalide : laisser la chaîne continuer sans authentification
            filterChain.doFilter(request, response);
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(token, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
