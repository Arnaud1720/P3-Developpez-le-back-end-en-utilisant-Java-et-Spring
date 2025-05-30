package com.arnaud.p3.ChaTop.auth;

import com.arnaud.p3.ChaTop.utils.JwtUtils;
import com.arnaud.p3.ChaTop.utils.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter  extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final UserDetailsServiceImpl uds;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest req,
                                  @NonNull HttpServletResponse res,
                                  @NonNull FilterChain chain)
    throws ServletException, IOException {

    String token = parseJwt(req);          // ← récupère le token

    if (token != null && jwtUtils.validateJwtToken(token)) {
      String email = jwtUtils.getEmailFromJwtToken(token);

      UserDetails user = uds.loadUserByUsername(email);

      UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(
          user, null, user.getAuthorities());

      auth.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(req));

      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    chain.doFilter(req, res);
  }

  /** Extrait le JWT de l’en-tête Authorization, sans tenir compte de la casse */
  private String parseJwt(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (header != null && header.toLowerCase().startsWith("bearer ")) {
      return header.substring(7).trim();   // trim() enlève un éventuel espace résiduel
    }
    return null;
  }
}
