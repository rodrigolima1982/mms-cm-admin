package com.mms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "template", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class Template {

	public Template(@NotBlank @Size(max = 20) String name, @NotBlank @Size(max = 30) String subject, String description,
			Set<Slide> slides) {
		super();
		this.name = name;
		this.subject = subject;
		this.description = description;
		this.slides = slides;
	}

	public Template() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String name;

	@NotBlank
	@Size(max = 30)
	private String subject;

	private String description;

	@OneToMany(mappedBy = "template", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Slide> slides = new HashSet<Slide>();

	@OneToMany(mappedBy = "template")
	private Set<Campaign> campaigns;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(10) default 'ENABLED'")
	private EStatus status = EStatus.ENABLED;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(Set<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}
	
}
