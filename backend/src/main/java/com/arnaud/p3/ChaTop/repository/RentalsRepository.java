package com.arnaud.p3.ChaTop.repository;

import com.arnaud.p3.ChaTop.dto.MessageDto;
import com.arnaud.p3.ChaTop.dto.RentalsDto;
import com.arnaud.p3.ChaTop.entity.Rentals;
import com.arnaud.p3.ChaTop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RentalsRepository extends JpaRepository<Rentals, Integer> {
}
