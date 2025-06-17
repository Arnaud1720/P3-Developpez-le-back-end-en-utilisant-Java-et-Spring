package com.arnaud.p3.ChaTop.controller;

import com.arnaud.p3.ChaTop.dto.MessageDto;
import com.arnaud.p3.ChaTop.exception.ErrorDto;
import com.arnaud.p3.ChaTop.services.MessageServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")

public class MessageController {
  private final MessageServices messageServices;


  public MessageController(MessageServices messageServices) {
    this.messageServices = messageServices;
  }

  @Operation(summary = "Enregistre un nouveau message")
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Message enregistré avec succès",
      content = @Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = MessageDto.class)
      )
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Erreur serveur lors de l'enregistrement du message",
      content = @Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ErrorDto.class)
      )
    )
  })
  @PostMapping("/save")
  public ResponseEntity<MessageDto> save(@RequestBody MessageDto messageDto) {
    messageServices.save(messageDto);
    return ResponseEntity.status(HttpStatus.OK)
      .body(messageDto);
  }
}
