package com.mms.vo;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.mms.model.Slide;
import com.mms.util.dto.DTOEntity;

public class CreateTemplateDto implements DTOEntity {
	
	@NotNull
	private String name;

	@NotNull
	private String subject;

	private String description;

	private Set<Slide> slides;

	public CreateTemplateDto(@NotNull Long id, @NotNull String name, @NotNull String subject, String description,
			Set<Slide> slides) {
		super();
		this.name = name;
		this.subject = subject;
		this.description = description;
		this.slides = slides;
	}
	

	public CreateTemplateDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Slide> getSlides() {
		return slides;
	}

	public void setSlides(Set<Slide> slides) {
		this.slides = slides;
	}

}
