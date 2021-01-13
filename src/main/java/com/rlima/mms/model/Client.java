package com.rlima.mms.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "client", indexes = @Index(name = "un_idx_msisdn", columnList = "msisdn", unique = true))
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String msisdn;
	
	private String plan;
	
	private String status;
	
	private boolean isMmsCapable=false;
	
	private int campaignParticipationCount = 0;
	
	private int campaignParticipationCountHistory=0;
	
	@ManyToMany(mappedBy = "clients")
	private Set<Campaign> campaigns;

	public Client(@NotBlank String msisdn, String plan, String status, boolean isMmsCapable,
			int campaignParticipationCount, int campaignParticipationCountHistory) {
		super();
		this.msisdn = msisdn;
		this.plan = plan;
		this.status = status;
		this.isMmsCapable = isMmsCapable;
		this.campaignParticipationCount = campaignParticipationCount;
		this.campaignParticipationCountHistory = campaignParticipationCountHistory;
	}
	
	public Client() {
		super();
	}

	public boolean isMmsCapable() {
		return isMmsCapable;
	}

	public void setMmsCapable(boolean isMmsCapable) {
		this.isMmsCapable = isMmsCapable;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public int getCampaignParticipationCount() {
		return campaignParticipationCount;
	}

	public void setCampaignParticipationCount(int campaignParticipationCount) {
		this.campaignParticipationCount = campaignParticipationCount;
	}
	

	public int getCampaignParticipationCountHistory() {
		return campaignParticipationCountHistory;
	}

	public void setCampaignParticipationCountHistory(int campaignParticipationCountHistory) {
		this.campaignParticipationCountHistory = campaignParticipationCountHistory;
	}

	public Set<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(Set<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

}
