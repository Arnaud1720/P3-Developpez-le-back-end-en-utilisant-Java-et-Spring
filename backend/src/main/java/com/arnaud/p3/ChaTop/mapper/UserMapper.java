package com.arnaud.p3.ChaTop.mapper;

import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.dto.out.UserOutputDto;
import com.arnaud.p3.ChaTop.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UsersDto toDTO(Users user);

  Users toEntity(UsersDto userDTO);

//  @Mapping(target = "password", ignore = true)
  UserOutputDto toOutputDto(Users user);
}
