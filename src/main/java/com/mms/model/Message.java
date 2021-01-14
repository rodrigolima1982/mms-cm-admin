package com.mms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;



@Entity
@Table(name = "message")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@ManyToOne
    private Client client;
	
	@NotBlank
	@ManyToOne
    private Campaign campaign;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private MessageStatus status;

	public Message(@NotBlank Client client, @NotBlank Campaign campaign, MessageStatus status) {
		super();
		this.client = client;
		this.campaign = campaign;
		this.status = status;
	}

	public Message() {
		super();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Long getId() {
		return id;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}
	
}
