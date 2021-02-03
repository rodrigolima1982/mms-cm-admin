package com.mms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mms.model.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

	Optional<Template> findById(Long id);
	
	Page<Template> findAll(Pageable pageable);
	
	Page <Template> findByNameContaining(String name, Pageable pageable);
	
	Template findByName(String name);
	
	@Query("from Template t join FETCH t.slides where t.id=:id and t.status='ENABLED'")
	Optional<Template> findByIdAndFetchSlidesEagerly(@Param("id") Long id);
}
