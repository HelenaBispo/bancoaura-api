package br.com.bancoaura.internetbanking.auxiliares;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

import static br.com.bancoaura.internetbanking.auxiliares.AuxiliarConstantes.SEGREDO_TOKEN_JWT;
import static br.com.bancoaura.internetbanking.auxiliares.AuxiliarConstantes.VALIDADE_TOKEN_JWT;

@Component
public class AuxiliarJwt {
    // Método para gerar um token JWT
    public String gerarToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + VALIDADE_TOKEN_JWT))  // Expira em 10 horas
                .sign(Algorithm.HMAC256(SEGREDO_TOKEN_JWT));
    }

    // Método para extrair o username do token
    public String extrairConta(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SEGREDO_TOKEN_JWT)).build().verify(token);
        return decodedJWT.getSubject();
    }

    // Método para validar o token
    public boolean validarToken(String token, String username) {
        String extractedUsername = extrairConta(token);
        return extractedUsername.equals(username) && !verificarTokenExpirado(token);
    }

    // Método para verificar se o token está expirado
    private boolean verificarTokenExpirado(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SEGREDO_TOKEN_JWT)).build().verify(token);
        return decodedJWT.getExpiresAt().before(new Date());
    }
}
