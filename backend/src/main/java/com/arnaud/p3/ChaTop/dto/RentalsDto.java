package com.arnaud.p3.ChaTop.dto;

import com.arnaud.p3.ChaTop.entity.Message;
import com.arnaud.p3.ChaTop.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"messages"})
public class RentalsDto {
    @JsonIgnore
    private Integer id;

    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  @Schema(example = "1")
  private Integer ownerId;
    private List<Message> messages;


}
