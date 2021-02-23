package com.mms.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.mms.util.dto.DTOEntity;

public class SlideDto implements DTOEntity {

	@NotEmpty
	@Size(max = 160)
	private String text;

	@Min(1)
	private int duration;

	public SlideDto() {
		super();
	}

	public SlideDto(@NotBlank @Size(max = 160) String text, @Min(1) int duration) {
		super();
		this.text = text;
		this.duration = duration;

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

}
