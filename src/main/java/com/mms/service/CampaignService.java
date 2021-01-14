package com.mms.service;

import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.mms.model.Campaign;
import com.mms.repository.CampaignRepository;

@ManagedBean
public class CampaignService {
	
	@Autowired
	private CampaignRepository campaignRepository;

	/**
	 * Create a new campaign in the database.
	 * @param campaign
	 * @return
	 */
	public Campaign create(Campaign campaign) {
		
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
