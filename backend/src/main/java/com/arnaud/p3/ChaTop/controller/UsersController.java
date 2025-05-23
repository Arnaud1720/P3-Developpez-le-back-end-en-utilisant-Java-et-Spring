package com.arnaud.p3.ChaTop.controller;

import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.services.UsersServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersServices userService;

    public UsersController(UsersServices userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UsersDto> create(@RequestBody UsersDto usersDto) {
        UsersDto created = userService.save(usersDto);
        URI location = URI.create("/api/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }
}
