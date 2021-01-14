package com.mms.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mms.model.Campaign;
import com.mms.model.User;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

	List<Campaign> findAllByStartDateBetween(Date campaignTimeStart, Date campaignTimeEnd);
	
	Optional<Campaign> findByName(String name);
}
