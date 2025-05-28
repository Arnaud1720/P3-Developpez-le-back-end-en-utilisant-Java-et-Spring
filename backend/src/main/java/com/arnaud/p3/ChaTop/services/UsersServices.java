package com.arnaud.p3.ChaTop.services;

import com.arnaud.p3.ChaTop.dto.UsersDto;

public interface UsersServices {
    UsersDto save(UsersDto usersDto);
    UsersDto findByName(String username);
    UsersDto findByEmail(String email);
    UsersDto findById(int id);
    void deleteById(int id);

}
