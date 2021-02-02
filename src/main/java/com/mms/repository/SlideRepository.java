package com.mms.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mms.model.Slide;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
	
	@Query("from Slide s where s.template.id=:id")
	Set<Slide> findByTemplateId(long id);

}
