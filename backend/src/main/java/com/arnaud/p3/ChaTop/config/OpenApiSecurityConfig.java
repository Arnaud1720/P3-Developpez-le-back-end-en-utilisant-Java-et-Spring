package com.arnaud.p3.ChaTop.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
  security = @SecurityRequirement(name = "bearerAuth")   // appliqué globalement
)
@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",          // ⚠️ exactement "bearer", sans espace ni majuscule
  bearerFormat = "JWT",
  in = SecuritySchemeIn.HEADER
)
public class OpenApiSecurityConfig {

}
