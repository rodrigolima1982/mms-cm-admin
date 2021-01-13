package com.rlima.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlima.mms.model.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

}
