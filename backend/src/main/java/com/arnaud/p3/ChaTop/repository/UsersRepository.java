package com.arnaud.p3.ChaTop.repository;

import com.arnaud.p3.ChaTop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

  @Repository
  public interface UsersRepository extends JpaRepository<Users, Integer> {
      Optional<Users> findByEmail(String email);
      Optional<Users> findByName(String name);
      Optional<Users> deleteById(int id);
  }
