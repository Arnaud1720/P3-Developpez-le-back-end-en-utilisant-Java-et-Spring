package com.arnaud.p3.ChaTop.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputDto {
  private Integer id;
  private String name;
  private String email;
  private String password;
  private LocalDateTime createdAt;
}
