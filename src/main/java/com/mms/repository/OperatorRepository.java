package com.mms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mms.model.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
	
	Optional<Operator> findById(Long id);

}
