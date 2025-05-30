package com.arnaud.p3.ChaTop.services.impl;

import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.entity.Users;
import com.arnaud.p3.ChaTop.mapper.UserMapper;
import com.arnaud.p3.ChaTop.repository.UsersRepository;
import com.arnaud.p3.ChaTop.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UsersServicesImpl implements UsersServices {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UsersServicesImpl(UsersRepository usersRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UsersDto save(UsersDto usersDto) {
      // 1. Convertir le DTO en entité
      Users entity = userMapper.toEntity(usersDto);

      // 2. Initialiser les dates sur l’entité
      LocalDateTime now = LocalDateTime.now();
      entity.setUpdatedAt(now);

      if (entity.getId() == null) {
        entity.setCreatedAt(now);
      }
      // 3. Sauvegarder en base
      entity.setPassword(bCryptPasswordEncoder.encode(usersDto.getPassword()));
      Users saved = usersRepository.save(entity);

      // 4. Retourner un DTO « rafraîchi »
      return userMapper.toDTO(saved);
    }

    @Override
    public UsersDto findByName(String name) {
      Users users = usersRepository.findByName(name).orElseThrow(
        () -> new RuntimeException("User with name " + name + " not found")
      );
      return userMapper.toDTO(users);
    }

    @Override
    public UsersDto findByEmail(String email) {
        Users users = usersRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("User not found"));

        return userMapper.toDTO(users);
    }

    @Override
    public UsersDto findById(int id) {
        Users users = usersRepository.findById(id).orElse(null);
        return userMapper.toDTO(users);
    }

  @Override
  public void deleteById(int id) {
    usersRepository.deleteById(id);
  }

  @Override
  public Optional<Users> findByNameOrEmail(String name, String email) {
    return Optional.empty();
  }

  @Override
  public boolean existsByEmail(String email) {
    return false;
  }

  @Override
  public boolean existsByName(String name) {
    return false;
  }
}
