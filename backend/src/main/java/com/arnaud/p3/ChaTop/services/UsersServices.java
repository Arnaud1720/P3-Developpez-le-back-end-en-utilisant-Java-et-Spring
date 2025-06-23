package com.arnaud.p3.ChaTop.services;

import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.dto.out.UserOutputDto;

public interface UsersServices {
    UsersDto save(UsersDto usersDto);
    UsersDto findByName(String username);
    UsersDto findByEmail(String email);
    UserOutputDto findById(int id);
    void deleteById(int id);
    UsersDto update(UsersDto usersDto);
}
