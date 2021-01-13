package com.rlima.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlima.mms.model.Slide;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {

}
