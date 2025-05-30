package com.arnaud.p3.ChaTop.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "JwtResponse", description = "Réponse après authentification réussie")
public class JwtResponse  {
  @Schema(description = "Le token JWT", example = "eyJhbGciOiJIUzI1Ni...")
  private String token;

  @Schema(description = "Type de token", example = "Bearer")
  private String type;

  @Schema(description = "ID de l’utilisateur", example = "1")
  private Integer id;

  @Schema(description = "Email de l’utilisateur", example = "john.doe@example.com")
  private String email;

  @Schema(description = "Liste des rôles", example = "[\"ROLE_USER\"]")
  private List<String> roles;
}
