package com.arnaud.p3.ChaTop.dto;

import com.arnaud.p3.ChaTop.entity.Rental;
import com.arnaud.p3.ChaTop.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Integer id;

    private String message;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Users user;

    private Rental rental;
}
