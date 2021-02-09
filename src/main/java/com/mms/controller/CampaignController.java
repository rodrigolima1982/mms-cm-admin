package com.mms.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mms.dto.CampaignDto;
import com.mms.model.Campaign;
import com.mms.service.CampaignService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mms-sender/campaign")
public class CampaignController {

	@Autowired
	private CampaignService campaignService;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public Campaign create(@RequestBody CampaignDto campaignDto) {
		try {
			return campaignService.create(campaignDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error Creating the Campaign", e);
		}

	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@RequestMapping(value = "/list/{startDate}/{endDate}", method = RequestMethod.GET)
	public List<Campaign> listByDateRange(@PathVariable Date startDate, @PathVariable Date endDate) {

		try {
			return campaignService.listCampaignByDateRange(startDate, endDate);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error Listing the Campaign", e);
		}
		
	}
}
