package com.arnaud.p3.ChaTop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private int id;               // <-- indispensable pour getId()
    private String email;
    private String name;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // getter explicite :
    public Integer getId() {
        return this.id;
    }
}