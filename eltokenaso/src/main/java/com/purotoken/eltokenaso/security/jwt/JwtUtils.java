package com.purotoken.eltokenaso.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
    
    private SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private Long timeExpiration = 1000*60*60*24L;

    //Generar token de acceso
    public String generateAccesToken(String username){
        return Jwts.builder()
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+timeExpiration))
        .signWith(secretKey).compact();
    }

    //Validar Token de acceso
    public boolean isTokenValid(String token){
       try {
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return true;
       } catch (Exception e) {
        log.error("\nToken invalido" + e.getMessage()+ "\n");
            return false;
       }
    }

    //tener claims del token 
    public Claims getClaims(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
    // obtener un solo claim
    public <T> T getclaim(String token, Function<Claims, T> claimsFunction){
        Claims claims = getClaims(token);
        return claimsFunction.apply(claims);
    }

    //Obtener username
    public String getUsernameFromToken(String token){
        return getclaim(token, Claims::getSubject);
    }

    // public key getSignature(){
    //     byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    //     return Keys.hmacShaKeyFor(keyBytes);
    // }
    
}
