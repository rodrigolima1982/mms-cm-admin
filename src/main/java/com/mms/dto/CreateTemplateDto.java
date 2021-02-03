package com.mms.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.mms.util.dto.DTOEntity;

public class CreateTemplateDto implements DTOEntity {
	
	@NotNull
	private String name;

	@NotNull
	private String subject;

	private String description;

	private Set<SlideDTO> slidesDTO = new HashSet<SlideDTO>();


	public CreateTemplateDto(@NotNull String name, @NotNull String subject, String description, Set<SlideDTO> slides) {
		super();
		this.name = name;
		this.subject = subject;
		this.description = description;
		this.slidesDTO = slides;
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

	public Set<SlideDTO> getSlides() {
		return slidesDTO;
	}

	public void setSlides(Set<SlideDTO> slides) {
		this.slidesDTO = slides;
	}

}
