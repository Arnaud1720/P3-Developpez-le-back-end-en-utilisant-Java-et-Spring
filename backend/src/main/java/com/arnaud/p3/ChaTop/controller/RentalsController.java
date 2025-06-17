package com.arnaud.p3.ChaTop.controller;

import com.arnaud.p3.ChaTop.dto.RentalsDto;
import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.exception.ErrorDto;
import com.arnaud.p3.ChaTop.services.RentalServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")

public class RentalsController {
  private final RentalServices service;

  public RentalsController(RentalServices service) {
    this.service = service;
  }

  @Operation(summary = "Crée une nouvelle location")
  @ApiResponses({
    @ApiResponse(
      responseCode = "201",
      description = "Location créée avec succès",
      content = @Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = RentalsDto.class),
        examples = @ExampleObject(
          name = "SuccessExample",
          value = "{ \"id\": 123, \"startDate\": \"2025-06-17\", \"endDate\": \"2025-06-20\", \"message\": \"Rental created !\" }"
        )
      )
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Données de location invalides",
      content = @Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ErrorDto.class),
        examples = @ExampleObject(
          name = "BadRequestExample",
          value = "{ \"code\": \"VALIDATION_ERROR\", \"message\": \"Le champ startDate est obligatoire\" }"
        )
      )
    )
  })
  @PostMapping("/save")
  public ResponseEntity<Map<String,Object>> createRental(
    @RequestBody @Valid RentalsDto dto
  ) {
    RentalsDto created = service.saveRental(dto);

    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(created.getId())
      .toUri();

    Map<String,Object> body = new HashMap<>();
    body.put("message", "Rental created !");
    body.put("rental", created);

    return ResponseEntity
      .created(location)
      .body(body);
  }

  @Operation(summary = "Récupère la liste de toutes les locations")
  @ApiResponse(
    responseCode = "200",
    description = "Liste des locations",
    content = @Content(
      mediaType = MediaType.APPLICATION_JSON_VALUE,
      schema = @Schema(implementation = RentalsDto.class)
    )
  )
  @GetMapping("/list")
  public ResponseEntity<Collection<RentalsDto>> getAllRentals() {
    return ResponseEntity.ok(service.getAllRentals());
  }

  @Operation(summary = "Récupère une location par son ID")
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Location trouvée",
      content = @Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = RentalsDto.class)
      )
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Location non trouvée",
      content = @Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ErrorDto.class),
        examples = @ExampleObject(
          name = "NotFoundExample",
          value = "{ \"code\": \"NOT_FOUND\", \"message\": \"Rental with ID 999 not found\" }"
        )
      )
    )
  })
  @GetMapping("/find-rentals/{id}")
  public ResponseEntity<RentalsDto> findRentalById(@PathVariable int id) {
    return ResponseEntity.ok(service.findRentalById(id));
  }

  @Operation(summary = "Supprime une location par son ID")
  @ApiResponses({
    @ApiResponse(
      responseCode = "204",
      description = "Location supprimée avec succès",
      content = @Content  // pas de body
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Location non trouvée",
      content = @Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ErrorDto.class),
        examples = @ExampleObject(
          name = "NotFoundDeleteExample",
          value = "{ \"code\": \"NOT_FOUND\", \"message\": \"Rental with ID 999 not found\" }"
        )
      )
    )
  })
  @DeleteMapping("/delete-rentals/{id}")
  public ResponseEntity<Void> deleteRentalById(@PathVariable int id) {
    service.deleteRentalById(id);
    return ResponseEntity.noContent().build();
  }
  @PutMapping(path = "/rentals/{rentalId}",
  consumes = MediaType.APPLICATION_JSON_VALUE,
  produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RentalsDto> updateRental(
    @PathVariable int rentalId,
    @RequestBody @Valid RentalsDto dto
  ) {
    dto.setId(rentalId);
    RentalsDto updated = service.updateRental(dto);
    return ResponseEntity.ok(updated);
  }


}
