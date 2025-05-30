package com.arnaud.p3.ChaTop.controller;

import com.arnaud.p3.ChaTop.auth.JwtResponse;
import com.arnaud.p3.ChaTop.auth.LoginRequest;
import com.arnaud.p3.ChaTop.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUser(
    @Valid @RequestBody LoginRequest loginRequest
  ) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getEmail(),
        loginRequest.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.generateJwtToken(authentication);
    var userDetails = (com.arnaud.p3.ChaTop.utils.UserDetailsImpl) authentication.getPrincipal();
    var roles = userDetails.getAuthorities().stream()
      .map(a -> a.getAuthority())
      .collect(Collectors.toList());

    JwtResponse response = new JwtResponse(jwt, "Bearer",
      userDetails.getId(), userDetails.getUsername(), roles);

    return ResponseEntity.ok(response);
  }

}
