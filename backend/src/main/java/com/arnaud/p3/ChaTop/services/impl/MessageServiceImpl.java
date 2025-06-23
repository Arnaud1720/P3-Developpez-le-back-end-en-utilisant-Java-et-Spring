package com.arnaud.p3.ChaTop.services.impl;

import com.arnaud.p3.ChaTop.dto.MessageDto;
import com.arnaud.p3.ChaTop.entity.Message;
import com.arnaud.p3.ChaTop.entity.Rentals;
import com.arnaud.p3.ChaTop.entity.Users;
import com.arnaud.p3.ChaTop.mapper.MessageMapper;
import com.arnaud.p3.ChaTop.repository.MessageRepository;
import com.arnaud.p3.ChaTop.repository.RentalsRepository;
import com.arnaud.p3.ChaTop.repository.UsersRepository;
import com.arnaud.p3.ChaTop.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class MessageServiceImpl implements MessageServices {
  private final MessageRepository messageRepository;
  private final UsersRepository usersRepository;
  private final RentalsRepository rentalsRepository;
  private final MessageMapper messageMapper;
  @Autowired
  public MessageServiceImpl(MessageRepository messageRepository, UsersRepository usersRepository, RentalsRepository rentalsRepository, MessageMapper messageMapper) {
    this.messageRepository = messageRepository;
    this.usersRepository = usersRepository;
    this.rentalsRepository = rentalsRepository;
    this.messageMapper = messageMapper;
  }

  @Override
  public MessageDto save(MessageDto dto) {

    // 1. map DTO -> entity (sans relations)
    Message entity = messageMapper.toEntity(dto);

    // 2. récupérer et fixer les associations
    Users   user   = usersRepository.findById(dto.getUserId())
      .orElseThrow(() -> new IllegalArgumentException(
        "User not found id=" + dto.getUserId()));
    Rentals rental = rentalsRepository.findById(dto.getRentalsId())
      .orElseThrow(() -> new IllegalArgumentException(
        "Rental not found id=" + dto.getRentalsId()));

    entity.setUser(user);
    entity.setRentals(rental);

    // 3. gérer les dates
    LocalDateTime now = LocalDateTime.now();
    entity.setUpdatedAt(now);
    if (entity.getId() == null) {
      entity.setCreatedAt(now);
    }

    // 4. persister
    Message saved = messageRepository.save(entity)  ;

    // 5. renvoyer la représentation à jour
   return messageMapper.toDTO(saved);
  }


}
