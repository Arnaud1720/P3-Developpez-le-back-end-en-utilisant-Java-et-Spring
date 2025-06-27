package com.arnaud.p3.ChaTop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UsersDto", description = "Représentation d'un utilisateur")
public class UsersDto {

  @Schema(description = "Identifiant généré", example = "1",
    accessMode = Schema.AccessMode.READ_ONLY)
  private Integer id;

  @Schema(description = "Adresse e-mail", example = "john.doe@example.com", required = true)
  private String email;

  @Schema(description = "Nom complet", example = "John Doe", required = true)
  private String name;

  @Schema(description = "Mot de passe (en clair pour la création)", example = "s3cr3tP@ss", required = true)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Schema(description = "Date de création", accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDateTime createdAt;

  @Schema(description = "Date de mise à jour", accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDateTime updatedAt;


}
