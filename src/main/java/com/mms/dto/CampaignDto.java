package com.mms.dto;

import com.mms.util.dto.DTOEntity;

public class CampaignDto extends BasicCampaignDto implements DTOEntity {
	
	private Long id;
	
	public CampaignDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
