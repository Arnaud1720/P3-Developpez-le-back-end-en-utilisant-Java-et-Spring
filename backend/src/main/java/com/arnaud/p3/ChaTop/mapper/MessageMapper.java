package com.arnaud.p3.ChaTop.mapper;

import com.arnaud.p3.ChaTop.dto.MessageDto;
import com.arnaud.p3.ChaTop.dto.RentalsDto;
import com.arnaud.p3.ChaTop.entity.Message;
import com.arnaud.p3.ChaTop.entity.Rentals;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageDto toDTO(Message message);

    Message toEntity(MessageDto messageDto);
}
