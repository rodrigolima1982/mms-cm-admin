package com.rlima.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlima.mms.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
