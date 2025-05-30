package com.arnaud.p3.ChaTop.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
  // Durée de vie du token (1 h)
  private final long jwtExpirationMs = 3_600_000L;

  // Clé secrète HS512 (générée automatiquement, 512 bits)
  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  }

  public String generateJwtToken(Authentication authentication) {
    String email = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
    Date now = new Date();
    Date expiry = new Date(now.getTime() + jwtExpirationMs);

    return Jwts.builder()
      .setSubject(email)
      .setIssuedAt(now)
      .setExpiration(expiry)
      .signWith(key)
      .compact();
  }

  public String getEmailFromJwtToken(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      // loger éventuel : signature invalide, expiré, malformé…
      return false;
    }
  }
}
