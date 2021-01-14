package com.mms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "slide_image")
public class SlideImage {

	public SlideImage(String name, String type, byte[] picByte) {
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}

	public SlideImage() {
		super();
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String type;

	@NotBlank
	private String name;

	@Column(name = "picByte", length = 200000)
	private byte[] picByte;

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

	public byte[] getPicByte() {

		return picByte;

	}

	public void setPicByte(byte[] picByte) {

		this.picByte = picByte;

	}

}