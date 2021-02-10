package com.mms.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.mms.util.dto.DTOEntity;

public class SlideDto implements DTOEntity {
	
	@NotBlank
	@Size(max = 160)
	private String text;

	@Min(1)
	private int duration;

	@NotNull
	private MultipartFile image;

	public SlideDto() {
		super();
	}

	public SlideDto(@NotBlank @Size(max = 160) String text, int duration, @NotNull MultipartFile image) {
		super();
		this.text = text;
		this.duration = duration;
		this.image = image;
	}

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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}



}
