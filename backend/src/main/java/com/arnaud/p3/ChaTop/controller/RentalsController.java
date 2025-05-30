package com.arnaud.p3.ChaTop.controller;

import com.arnaud.p3.ChaTop.dto.RentalsDto;
import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.services.RentalServices;
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

  // Optionnel : récupérer toutes les locations
  @GetMapping("/list")
  public ResponseEntity<Collection<RentalsDto>> getAllRentals() {
    return ResponseEntity.ok(service.getAllRentals());
  }

  @GetMapping("/find-rentals/{id}")
  public ResponseEntity<RentalsDto> findRentalById(@PathVariable int id) {
    return ResponseEntity.ok(service.findRentalById(id));
  }

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
