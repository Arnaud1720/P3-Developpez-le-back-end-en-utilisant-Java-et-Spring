package com.arnaud.p3.ChaTop.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginRequest {
  @Schema(example="john.doe@example.com")
  @JsonProperty("email")    // pour bien binder le JSON
  private String email;
  private String password;
}
