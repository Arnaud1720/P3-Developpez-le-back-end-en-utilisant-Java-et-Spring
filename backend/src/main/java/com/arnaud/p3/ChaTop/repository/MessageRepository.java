package com.arnaud.p3.ChaTop.repository;

import com.arnaud.p3.ChaTop.entity.Message;
import com.arnaud.p3.ChaTop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
