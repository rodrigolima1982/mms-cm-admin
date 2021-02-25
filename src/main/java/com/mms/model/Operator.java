package com.mms.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(	name = "operator", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "name")
		})
public class Operator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String country;
	
	@OneToMany(mappedBy="operator") 
	private Set<Campaign> campaigns;
	
	@OneToMany(mappedBy="operator") 
	private Set<Template> templates;

	public Operator(Long id, @NotBlank String name, @NotBlank String country) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Set<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(Set<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public Long getId() {
		return id;
	}

}
