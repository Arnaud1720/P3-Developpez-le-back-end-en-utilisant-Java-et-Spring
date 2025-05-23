package com.arnaud.p3.ChaTop.services.impl;

import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.entity.Users;
import com.arnaud.p3.ChaTop.mapper.UserMapper;
import com.arnaud.p3.ChaTop.repository.UsersRepository;
import com.arnaud.p3.ChaTop.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class usersServicesImpl implements UsersServices {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Autowired
    public usersServicesImpl(UsersRepository usersRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UsersDto save(UsersDto usersDto) {
        // 1. Convertir le DTO en entité
        Users entity = userMapper.toEntity(usersDto);  // <> usersDto et non UsersDto !

        // 2. Sauvegarder en base
        Users saved = usersRepository.save(entity);

        // 3. Retourner un DTO « rafraîchi »
        return userMapper.toDTO(saved);
    }

    @Override
    public UsersDto findByUsername(String username) {
        return null;
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
}
