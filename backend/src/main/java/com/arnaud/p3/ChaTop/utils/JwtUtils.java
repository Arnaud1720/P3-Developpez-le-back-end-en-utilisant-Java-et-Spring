package com.arnaud.p3.ChaTop.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

  // Clé secrète HS512 (générée automatiquement, 512 bits)
  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  }

  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
    String email     = user.getUsername();
    String name = user.getName();
    Date now    = new Date();
    // Durée de vie du token (1 h)
    long jwtExpirationMs = 3_600_000L;
    Date expiry = new Date(now.getTime() + jwtExpirationMs);

    // 1) On prépare les claims
    Claims claims = Jwts.claims().setSubject(email);
    claims.put("Name", name);

    // 2) On génère le token avec ces claims
    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(expiry)
      .signWith(key)              // votre clé déjà initialisée
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

  public String getClaimFromJwtToken(String token, String claimKey) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody()
      .get(claimKey, String.class);
  }

}
