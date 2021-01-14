package com.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mms.model.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

	 public Template findByName(String name);
}
