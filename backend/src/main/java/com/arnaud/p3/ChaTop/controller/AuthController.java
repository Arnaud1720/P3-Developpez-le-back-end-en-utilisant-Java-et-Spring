package com.arnaud.p3.ChaTop.controller;

import com.arnaud.p3.ChaTop.auth.JwtResponse;
import com.arnaud.p3.ChaTop.auth.LoginRequest;
import com.arnaud.p3.ChaTop.dto.UserInfoResponse;
import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.exception.ErrorDto;
import com.arnaud.p3.ChaTop.services.UsersServices;
import com.arnaud.p3.ChaTop.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final UsersServices service;

  @Operation(summary = "Authentifie un utilisateur et retourne un JWT")
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Authentification réussie",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = JwtResponse.class),
        examples = @ExampleObject(
          name  = "SuccessExample",
          value = "{ \"token\": \"eyJhbGci...\", \"type\": \"Bearer\", \"id\": 42, \"username\": \"alice\", \"roles\": [\"ROLE_USER\"] }"
        )
      )
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Requête mal formée (DTO invalid)",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ErrorDto.class),
        examples = @ExampleObject(
          name  = "BadRequestExample",
          value = "{\"code\":\"VALIDATION_ERROR\",\"message\":\"Le champ password est obligatoire\"}"
        )
      )
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Identifiants invalides",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ErrorDto.class),
        examples = @ExampleObject(
          name  = "UnauthorizedExample",
          value = "{\"code\":\"UNAUTHORIZED\",\"message\":\"Email ou mot de passe incorrect\"}"
        )
      )
    )
  })
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
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());

    JwtResponse response = new JwtResponse(jwt, "Bearer",
      userDetails.getId(), userDetails.getUsername(), roles);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Enregistre un nouvel utilisateur")
  @ApiResponses({
    @ApiResponse(
      responseCode = "201",
      description = "Utilisateur créé avec succès",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = UsersDto.class),
        examples = @ExampleObject(
          name  = "CreatedExample",
          value = "{ \"id\": 42, \"username\": \"alice\", \"email\": \"alice@example.com\" }"
        )
      )
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Données invalides",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ErrorDto.class),
        examples = @ExampleObject(
          name  = "BadRequestExample",
          value = "{\"code\":\"VALIDATION_ERROR\",\"message\":\"Le champ email est obligatoire\"}"
        )
      )
    ),
    @ApiResponse(
      responseCode = "409",
      description = "Conflit, l’utilisateur existe déjà",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ErrorDto.class),
        examples = @ExampleObject(
          name  = "ConflictExample",
          value = "{\"code\":\"USER_EXISTS\",\"message\":\"Le nom d’utilisateur est déjà pris\"}"
        )
      )
    )
  })
  @PostMapping("/register")
  public ResponseEntity<UsersDto> createUtilisateur(
    @RequestBody @Valid UsersDto dto) {

    UsersDto created = service.save(dto);

    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(created.getId())
      .toUri();

    return ResponseEntity
      .created(location)
      .body(created);
  }


  @GetMapping("/me")
  public ResponseEntity<UserInfoResponse> getCurrentUser(
    @RequestHeader("Authorization") String authHeader
  ) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String token = authHeader.substring(7);
    if (!jwtUtils.validateJwtToken(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String email     = jwtUtils.getEmailFromJwtToken(token);
    String firstName = jwtUtils.getClaimFromJwtToken(token, "email");

    return ResponseEntity.ok(new UserInfoResponse(email));
  }


}

