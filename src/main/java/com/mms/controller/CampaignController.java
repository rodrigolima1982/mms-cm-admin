package com.mms.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mms.dto.BasicCampaignDto;
import com.mms.dto.CampaignDto;
import com.mms.dto.Pagination;
import com.mms.exception.RecordNotFoundException;
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
	public BasicCampaignDto create(@Valid @RequestBody BasicCampaignDto basicCampaignDto) {
		try {
			return campaignService.create(basicCampaignDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error Creating the Campaign", e);
		}

	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public CampaignDto update(@Valid @RequestBody CampaignDto campaignDto) {
		try {
			return campaignService.update(campaignDto);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error Updating the Campaign", e);
		}

	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> disableTemplate(@PathVariable String id) {
        try {
            campaignService.disable(Long.parseLong(id));
            return ResponseEntity.ok(Long.parseLong(id));
        } catch (NumberFormatException | RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Campaign> getCampaignById(@PathVariable String id){
		
		try {
			Optional<Campaign> campaignDto = campaignService.get(Long.parseLong(id));

            return ResponseEntity.ok().body(campaignDto.get());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<Pagination<Campaign>> getCampaigns(@RequestParam(required = false) String name,
                                                               @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Pagination<Campaign> response = campaignService.getCampaignListPaginated(page, size, name);
        	
        	return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
