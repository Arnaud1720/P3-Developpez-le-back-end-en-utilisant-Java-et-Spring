package com.arnaud.p3.ChaTop.services.impl;

import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.dto.out.UserOutputDto;
import com.arnaud.p3.ChaTop.entity.Role;
import com.arnaud.p3.ChaTop.entity.Users;
import com.arnaud.p3.ChaTop.mapper.UserMapper;
import com.arnaud.p3.ChaTop.repository.UsersRepository;
import com.arnaud.p3.ChaTop.services.UsersServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Transactional
public class UsersServicesImpl implements UsersServices {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersServicesImpl(UsersRepository usersRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UsersDto save(UsersDto usersDto) {
      Users entity = userMapper.toEntity(usersDto);
      LocalDateTime now = LocalDateTime.now();
      entity.setUpdatedAt(now);

      if (entity.getId() == null) {
        entity.setCreatedAt(now);
      }
      String rawPassword = usersDto.getPassword();
      if (rawPassword.startsWith("adm_")) {
        Role adminRole = new Role();
        adminRole.setId(1);
        adminRole.setName("ADMIN");
        entity.getRoles().add(adminRole);
      }else {
        Role userRole = new Role();
        userRole.setId(2);
        userRole.setName("USER");
      }
      entity.setPassword(bCryptPasswordEncoder.encode(usersDto.getPassword()));
      Users saved = usersRepository.save(entity);
      return userMapper.toDTO(saved);
    }

    @Override
    public UsersDto findByName(String name) {

      Users users = usersRepository.findByName(name).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User with name " + name + " not found")
      );

      return userMapper.toDTO(users);
    }

    @Override
    public UsersDto findByEmail(String email) {
        Users users = usersRepository.findByEmail(email).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        return userMapper.toDTO(users);
    }

    @Override
    public UserOutputDto findById(int id) {
        Users users = usersRepository.findById(id)
          .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return userMapper.toOutputDto(users);
    }

  @Override
  public void deleteById(int id) {
    usersRepository.deleteById(id);
  }

  @Override
  @Transactional
  public UsersDto update(UsersDto usersDto) {
    Users user = usersRepository.findById(usersDto.getId())
      .orElseThrow(() -> new EntityNotFoundException(
        "Utilisateur introuvable (id=" + usersDto.getId() + ")"));

    user.setEmail(usersDto.getEmail());
    user.setUpdatedAt(LocalDateTime.now());

    if (usersDto.getPassword() != null && !usersDto.getPassword().isBlank()) {
      user.setPassword(bCryptPasswordEncoder.encode(usersDto.getPassword()));
    }

    user.setUpdatedAt(LocalDateTime.now());

    Users updated = usersRepository.save(user);
    return userMapper.toDTO(updated);
  }

}
