package com.rlima.mms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlima.mms.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}
