package com.mms.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.mms.util.dto.DTOEntity;

public class TemplateDto implements DTOEntity {

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String subject;

	private String description;

	private Set<SlideDto> slides;

	public TemplateDto(@NotNull Long id, @NotNull String name, @NotNull String subject, String description,
			Set<SlideDto> slides) {
		super();
		this.id = id;
		this.name = name;
		this.subject = subject;
		this.description = description;
		this.slides = slides;
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
