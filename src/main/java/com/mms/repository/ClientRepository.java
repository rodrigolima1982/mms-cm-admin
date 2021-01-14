package com.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mms.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
