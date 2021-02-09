package com.mms.service;

import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.mms.dto.CampaignDto;
import com.mms.model.Campaign;
import com.mms.repository.CampaignRepository;
import com.mms.util.dto.DtoUtils;

@ManagedBean
public class CampaignService {
	
	@Autowired
	private CampaignRepository campaignRepository;

	/**
	 * Create a new campaign in the database.
	 * @param campaign
	 * @return
	 */
	public Campaign create(CampaignDto campaignDto) {
		
		Campaign campaign = (Campaign) new DtoUtils().convertToEntity(new Campaign(), campaignDto);
		
		return campaignRepository.save(campaign);
	}
	
	/**
	 * List campaign by startDate by date range.
	 * @param campaignTimeStart
	 * @param campaignTimeEnd
	 * @return
	 */
	public List<Campaign> listCampaignByDateRange(Date campaignTimeStart, Date campaignTimeEnd){
	
		return campaignRepository.findAllByStartDateBetween(campaignTimeStart, campaignTimeEnd);
		
	}
}
