package com.mms.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mms.model.Campaign;
import com.mms.model.EStatus;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
	
	Optional<Campaign> findByName(String name);
	
	Optional<Campaign> findById(Long id);
	
	Page<Campaign> findByStatus(EStatus status, Pageable pageable);
	
	Page <Campaign> findByNameContainingAndStatus(String name, EStatus status, Pageable pageable);
}
