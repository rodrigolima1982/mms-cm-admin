package com.mms.service;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.mms.model.Template;
import com.mms.repository.TemplateRepository;

@ManagedBean
public class TemplateService {
	
	@Autowired
	private TemplateRepository repository;
	
	public void setRepository(TemplateRepository repository) {
		this.repository=repository;
	}
	
	/**
	 * Create new template
	 * @param template
	 * @return
	 */
	public Template createTemplate(Template template) {
		return repository.save(template);
	}
	
	public Template get(Long id) {
		
		return repository.getOne(id);
	}

}
