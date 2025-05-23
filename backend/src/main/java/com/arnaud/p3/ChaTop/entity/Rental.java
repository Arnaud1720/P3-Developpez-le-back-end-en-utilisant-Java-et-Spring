package com.arnaud.p3.ChaTop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;

    @Column(length = 2000)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relations
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;

    @OneToMany(mappedBy = "rental")
    private List<Message> messages;
}
