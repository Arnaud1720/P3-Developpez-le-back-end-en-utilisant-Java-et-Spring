  package com.arnaud.p3.ChaTop.controller;

  import com.arnaud.p3.ChaTop.dto.UsersDto;
  import com.arnaud.p3.ChaTop.dto.out.UserOutputDto;
  import com.arnaud.p3.ChaTop.exception.ErrorResponse;
  import com.arnaud.p3.ChaTop.services.UsersServices;
  import io.swagger.v3.oas.annotations.Operation;
  import io.swagger.v3.oas.annotations.media.Content;
  import io.swagger.v3.oas.annotations.media.ExampleObject;
  import io.swagger.v3.oas.annotations.media.Schema;
  import io.swagger.v3.oas.annotations.responses.ApiResponse;
  import io.swagger.v3.oas.annotations.responses.ApiResponses;
  import jakarta.validation.Valid;
  import org.springframework.http.ResponseEntity;
  import org.springframework.security.access.prepost.PreAuthorize;
  import org.springframework.web.bind.annotation.*;

  @RestController
  @RequestMapping("/api/users")

  public class UsersController {

    private final UsersServices service;

    public UsersController(UsersServices service) {
      this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un utilisateur par son identifiant")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
      @ApiResponse(responseCode = "404", description = "Utilisateur introuvable",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class),
          examples = @ExampleObject(
            name = "NotFoundExample",
            value = "{\"code\":\"USER_NOT_FOUND\",\"message\":\"L’utilisateur avec cet ID n’existe pas\"}"
          )
        )
      )
    })
    public ResponseEntity<UserOutputDto> getUserById(@PathVariable int id) {
      UserOutputDto user = service.findById(id);
      return ResponseEntity.ok(user);
    }

    @GetMapping("by-name/{name}")
    @Operation(summary = "Récupère un utilisateur par son nom")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
      @ApiResponse(responseCode = "404", description = "Utilisateur introuvable",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class),
          examples = @ExampleObject(
            name = "NotFoundExample",
            value = "{\"code\":\"USER_NOT_FOUND\",\"message\":\"Aucun utilisateur trouvé avec ce nom\"}"
          )
        )
      )
    })
    public ResponseEntity<UsersDto> getUserByUsername(@PathVariable String name) {
      UsersDto user = service.findByName(name);
      return ResponseEntity.ok(user);
    }

    @GetMapping("by-email/{email}")
    @Operation(summary = "Récupère un utilisateur par son email")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
      @ApiResponse(responseCode = "404", description = "Utilisateur introuvable",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class),
          examples = @ExampleObject(
            name = "NotFoundExample",
            value = "{\"code\":\"USER_NOT_FOUND\",\"message\":\"Aucun utilisateur trouvé avec cet email\"}"
          )
        )
      )
    })
    public ResponseEntity<UsersDto> getUserByEmail(@PathVariable String email) {
      UsersDto user = service.findByEmail(email);
      return ResponseEntity.ok(user);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Permet à un administrateur de supprimer un utilisateur")
    @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
      @ApiResponse(responseCode = "401", description = "Vous devez être admin pour supprimer un utilisateur",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class),
          examples = @ExampleObject(
            name = "UnauthorizedExample",
            value = "{\"code\":\"UNAUTHORIZED\",\"message\":\"Vous devez être administrateur pour effectuer cette action\"}"
          )
        )
      ),
      @ApiResponse(responseCode = "404", description = "Utilisateur introuvable",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class),
          examples = @ExampleObject(
            name = "NotFoundExample",
            value = "{\"code\":\"USER_NOT_FOUND\",\"message\":\"Impossible de supprimer : utilisateur introuvable\"}"
          )
        )
      )
    })
    public ResponseEntity<Void> delete(@PathVariable int id) {
      service.deleteById(id);
      return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour les informations d’un utilisateur")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès"),
      @ApiResponse(responseCode = "400", description = "Requête invalide ou champs manquants",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class),
          examples = @ExampleObject(
            name = "BadRequestExample",
            value = "{\"code\":\"INVALID_DATA\",\"message\":\"Les champs fournis sont invalides\"}"
          )
        )
      ),
      @ApiResponse(responseCode = "404", description = "Utilisateur introuvable",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class),
          examples = @ExampleObject(
            name = "NotFoundExample",
            value = "{\"code\":\"USER_NOT_FOUND\",\"message\":\"Impossible de mettre à jour : utilisateur inexistant\"}"
          )
        )
      )
    })
    public ResponseEntity<UsersDto> updateUtilisateur(
      @PathVariable Integer id,
      @RequestBody @Valid UsersDto dto
    ) {
      dto.setId(id);
      UsersDto updated = service.update(dto);
      return ResponseEntity.ok(updated);
    }

  }
