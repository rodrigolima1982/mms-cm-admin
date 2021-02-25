package com.mms.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mms.util.dto.DTOEntity;

public class CreateTemplateDto implements DTOEntity {
	
	@NotEmpty
	private String name;

	@NotEmpty
	private String subject;

	private String description;
	
	@NotNull
	private Long operatorId;

	@NotEmpty
	private Set<SlideDto> slidesDTO = new HashSet<SlideDto>();


	public CreateTemplateDto(@NotEmpty String name, @NotEmpty String subject, String description, Long operatorId, @NotEmpty Set<SlideDto> slides) {
		super();
		this.name = name;
		this.subject = subject;
		this.description = description;
		this.slidesDTO = slides;
		this.operatorId = operatorId;
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

	public Set<SlideDto> getSlides() {
		return slidesDTO;
	}

	public void setSlides(Set<SlideDto> slides) {
		this.slidesDTO = slides;
	}

}
