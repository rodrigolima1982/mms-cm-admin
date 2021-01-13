package com.rlima.mms.service;

import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.rlima.mms.model.Campaign;
import com.rlima.mms.repository.CampaignRepository;

@ManagedBean
public class CampaignService {
	
	@Autowired
	private CampaignRepository campaignRepository;

	//public List<Campaign> listCampaignByDateRange(){
		
	//}
}
