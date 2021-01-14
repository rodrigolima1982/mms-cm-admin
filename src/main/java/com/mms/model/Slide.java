package com.mms.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "slide")
public class Slide {

	public Slide(@NotBlank @Size(max = 160) String text, @NotBlank int duration, @NotBlank SlideImage image) {
		super();
		this.text = text;
		this.duration = duration;
		this.image = image;
	}

	public Slide() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 160)
	private String text;

	@NotBlank
	private int duration;

	@NotBlank
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private SlideImage image;

	@ManyToOne
	private Template template;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public SlideImage getImage() {
		return image;
	}

	public void setImage(SlideImage image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

}
