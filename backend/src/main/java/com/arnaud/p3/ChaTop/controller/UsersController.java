package com.arnaud.p3.ChaTop.controller;

import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.services.UsersServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")

public class UsersController {

  private final UsersServices service;

  public UsersController(UsersServices service) {
    this.service = service;
  }


  @GetMapping("/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable int id) {
        UsersDto user = service.findById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("by-name/{name}")
  public ResponseEntity<UsersDto> getUserByUsername(@PathVariable String name) {
    UsersDto user = service.findByName(name);
    return ResponseEntity.ok(user);
    }
    @GetMapping("by-email/{email}")
  public ResponseEntity<UsersDto> getUserByEmail(@PathVariable String email) {
    UsersDto user = service.findByEmail(email);
    return ResponseEntity.ok(user);
    }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Void> delete(@PathVariable int id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
  @PutMapping("/{id}")
  public ResponseEntity<UsersDto> updateUtilisateur(
    @PathVariable Integer id,
    @RequestBody @Valid UsersDto dto
  ) {
    dto.setId(id);
    UsersDto updated = service.update(dto);
    return ResponseEntity.ok(updated);
  }

}
