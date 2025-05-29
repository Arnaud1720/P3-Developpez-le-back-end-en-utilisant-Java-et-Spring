package com.arnaud.p3.ChaTop.mapper;

import com.arnaud.p3.ChaTop.dto.RentalsDto;
import com.arnaud.p3.ChaTop.dto.UsersDto;
import com.arnaud.p3.ChaTop.entity.Rentals;
import com.arnaud.p3.ChaTop.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RentalsMapper {

    RentalsMapper INSTANCE = Mappers.getMapper(RentalsMapper.class);

    RentalsDto toDTO(Rentals rentals);

    Rentals toEntity(RentalsDto rentalsDto);
}
