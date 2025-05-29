package com.arnaud.p3.ChaTop.controller;

import com.arnaud.p3.ChaTop.dto.MessageDto;
import com.arnaud.p3.ChaTop.services.MessageServices;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/save")
  public ResponseEntity<MessageDto> save(@RequestBody MessageDto messageDto) {
    messageServices.save(messageDto);
    return ResponseEntity.status(HttpStatus.OK)
      .body(messageDto);
  }
}
