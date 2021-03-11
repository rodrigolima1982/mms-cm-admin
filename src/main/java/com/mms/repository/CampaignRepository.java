package com.mms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mms.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
	
	Optional<Campaign> findByName(String name);
	
	Optional<Campaign> findById(Long id);
	
	@Query("from Campaign c join FETCH c.template join FETCH c.operator where c.id=:id")
	Optional<Campaign> findByIdAndFetchTemplateAndOperatorEagerly(@Param("id") Long id);
}
