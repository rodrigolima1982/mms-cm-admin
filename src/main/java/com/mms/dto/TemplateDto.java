package com.mms.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mms.util.dto.DTOEntity;

public class TemplateDto implements DTOEntity {

	@NotNull
	private Long id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String subject;

	private String description;

	@NotEmpty
	private Set<SlideDto> slides;

	@NotNull
	private Long operatorId;

	public TemplateDto(@NotNull Long id, @NotEmpty String name, @NotEmpty String subject, String description,
			Set<SlideDto> slideDtos) {
		super();
		this.id = id;
		this.name = name;
		this.subject = subject;
		this.description = description;
		this.slides = slideDtos;

	}

	public TemplateDto() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
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
		return slides;
	}

	public void setSlides(Set<SlideDto> slides) {
		this.slides = slides;
	}

	public Long getId() {
		return id;
	}

}
