package com.mms.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mms.util.dto.DTOEntity;

public class CampaignDto implements DTOEntity {

	@NotBlank
	@Size(max = 20)
	private String name;

	@NotBlank
	private int senderShortNumber;

	private boolean isEnableDLR;

	private boolean isEnabledReadReceipt;
	
	private boolean canAnticipateStartTime;

	private LocalDate startDate;

	private LocalDate endDate;

	private int numberOfUsers;
	
	@NotNull
	private Long templateId;
	
	@NotNull
	private Long operatorId;
	
	public CampaignDto() {
		super();
	}

	public CampaignDto(@NotBlank @Size(max = 20) String name, @NotBlank int senderShortNumber, boolean isEnableDLR,
			boolean isEnabledReadReceipt, boolean canAnticipateStartTime, LocalDate startDate, LocalDate endDate,
			int numberOfUsers, @NotNull Long templateId, @NotNull Long operatorId) {
		super();
		this.name = name;
		this.senderShortNumber = senderShortNumber;
		this.isEnableDLR = isEnableDLR;
		this.isEnabledReadReceipt = isEnabledReadReceipt;
		this.canAnticipateStartTime = canAnticipateStartTime;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public boolean isCanAnticipateStartTime() {
		return canAnticipateStartTime;
	}

	public void setCanAnticipateStartTime(boolean canAnticipateStartTime) {
		this.canAnticipateStartTime = canAnticipateStartTime;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	
	
}
