package com.gaspar.facturador.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}") // Clave secreta para firmar el token
    private String privateKey;

    @Value("${security.jwt.user.generator}") // Identificador del emisor del token
    private String userGenerator;

    // Método para crear un token JWT
    public String createToken(Authentication authentication) {
        // Verifica que la autenticación no sea nula
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication cannot be null");
        }

        // Crea el algoritmo de firma usando la clave privada
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        // Obtiene el nombre de usuario y los roles/autoridades del usuario autenticado
        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Genera el token JWT
        return JWT.create()
                .withIssuer(this.userGenerator) // Quién emite el token
                .withSubject(username) // A quién pertenece el token
                .withClaim("authorities", authorities) // Roles/autoridades del usuario
                .withIssuedAt(new Date()) // Fecha de emisión
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)) // Expira en 30 minutos
                .withJWTId(UUID.randomUUID().toString()) // ID único del token
                .withNotBefore(new Date(System.currentTimeMillis())) // No válido antes de ahora
                .sign(algorithm); // Firma el token
    }

    // Método para validar un token JWT
    public DecodedJWT validateToken(String token) {
        try {
            // Crea el algoritmo de firma usando la clave privada
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            // Crea un verificador de tokens
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator) // Verifica el emisor
                    .build();

            // Verifica y decodifica el token
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            // Log del error (puedes usar un logger como SLF4J)
            System.err.println("Error validating token: " + exception.getMessage());
            throw new JWTVerificationException("Token invalid, not Authorized");
        }
    }

    // Método para extraer el nombre de usuario del token
    public String extractUsername(DecodedJWT decodedJWT) {
        if (decodedJWT == null) {
            throw new IllegalArgumentException("DecodedJWT cannot be null");
        }
        return decodedJWT.getSubject(); // Devuelve el sujeto (nombre de usuario)
    }

    // Método para obtener un claim específico del token
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        if (decodedJWT == null || claimName == null || claimName.isEmpty()) {
            throw new IllegalArgumentException("DecodedJWT and claimName cannot be null or empty");
        }
        return decodedJWT.getClaim(claimName); // Devuelve el claim solicitado
    }
}