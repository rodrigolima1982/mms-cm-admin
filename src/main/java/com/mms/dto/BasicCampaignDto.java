package com.mms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mms.util.dto.DTOEntity;

@Validated
public class BasicCampaignDto implements DTOEntity {

	@NotBlank
	@Size(max = 20)
	private String name;

	@NotBlank
	private int senderShortNumber;

	private boolean isEnableDLR;

	private boolean isEnabledReadReceipt;

	private int numberOfUsers;
	
	@NotNull
	private Long templateId;
	
	@NotNull
	@JsonIgnore
	private Long operatorId;
	
	public BasicCampaignDto() {
		super();
	}

	public BasicCampaignDto(@NotBlank @Size(max = 20) String name, @NotBlank int senderShortNumber, boolean isEnableDLR,
			boolean isEnabledReadReceipt, int numberOfUsers, @NotNull Long templateId, @NotNull Long operatorId) {
		super();
		this.name = name;
		this.senderShortNumber = senderShortNumber;
		this.isEnableDLR = isEnableDLR;
		this.isEnabledReadReceipt = isEnabledReadReceipt;
		this.numberOfUsers = numberOfUsers;
		this.templateId = templateId;
		this.operatorId = operatorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSenderShortNumber() {
		return senderShortNumber;
	}

	public void setSenderShortNumber(int senderShortNumber) {
		this.senderShortNumber = senderShortNumber;
	}

	public boolean isEnableDLR() {
		return isEnableDLR;
	}

	public void setEnableDLR(boolean isEnableDLR) {
		this.isEnableDLR = isEnableDLR;
	}

	public boolean isEnabledReadReceipt() {
		return isEnabledReadReceipt;
	}

	public void setEnabledReadReceipt(boolean isEnabledReadReceipt) {
		this.isEnabledReadReceipt = isEnabledReadReceipt;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	@IgnoreForBinding
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	@IgnoreForBinding
	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	
	
}
