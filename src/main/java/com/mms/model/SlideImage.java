package com.mms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "slide_image")
public class SlideImage {

	public SlideImage() {
		super();
	}

	public SlideImage(@NotBlank String type, @NotBlank String name, byte[] data) {
		super();
		this.type = type;
		this.name = name;
		this.data = data;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String type;

	@NotBlank
	private String name;

	@Lob
	private byte[] data;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getType() {

		return type;

	}

	public void setType(String type) {

		this.type = type;

	}

	public byte[] getData() {

		return data;

	}

	public void setPicByte(byte[] data) {

		this.data = data;

	}

}