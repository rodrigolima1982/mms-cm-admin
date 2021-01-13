package com.rlima.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlima.mms.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
