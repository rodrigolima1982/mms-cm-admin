package com.mms.service;

import java.util.Optional;

import javax.annotation.ManagedBean;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.mms.dto.BasicCampaignDto;
import com.mms.dto.CampaignDto;
import com.mms.dto.Pagination;
import com.mms.exception.RecordNotFoundException;
import com.mms.model.Campaign;
import com.mms.model.EStatus;
import com.mms.model.Operator;
import com.mms.model.Template;
import com.mms.repository.CampaignRepository;
import com.mms.repository.OperatorRepository;
import com.mms.repository.TemplateRepository;
import com.mms.util.dto.DtoUtils;

@ManagedBean
@Validated
public class CampaignService {

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private TemplateRepository templateRepository;

	@Autowired
	private OperatorRepository operatorRepository;

	/**
	 * Create a new campaign.
	 * 
	 * @param basicCampaignDto
	 * @return
	 * @throws ConstraintViolationException
	 * @throws RecordNotFoundException
	 */
	public BasicCampaignDto create(@Valid BasicCampaignDto basicCampaignDto)
			throws ConstraintViolationException, RecordNotFoundException {

		PropertyMap<BasicCampaignDto, Campaign> skipModifiedFieldsMap = new PropertyMap<BasicCampaignDto, Campaign>() {
			protected void configure() {
				skip().setId(null);
			}
		};

		Campaign campaign = (Campaign) new DtoUtils().convertToCampaignEntity(new Campaign(), basicCampaignDto,
				skipModifiedFieldsMap);

		Campaign createdCampaign = saveCampaign(basicCampaignDto, campaign);

		return (BasicCampaignDto) new DtoUtils().convertToDto(createdCampaign, new BasicCampaignDto());

	}

	/**
	 * Update an existing campaign.
	 * 
	 * @param campaignDto
	 * @return
	 * @throws RecordNotFoundException
	 */
	public CampaignDto update(@Valid CampaignDto campaignDto) throws RecordNotFoundException {

		Optional<Campaign> existing = campaignRepository.findById(campaignDto.getId());

		if (existing.isPresent()) {
			Campaign upatedCampaign = (Campaign) new DtoUtils().convertToEntity(new Campaign(), campaignDto);

			upatedCampaign = saveCampaign(campaignDto, upatedCampaign);
			return (CampaignDto) new DtoUtils().convertToDto(upatedCampaign, new CampaignDto());
		} else {
			throw new RecordNotFoundException("Campaign not found for the given id: " + campaignDto.getId());
		}
	}

	/**
	 * Generic method to save a campaign.
	 * 
	 * @param basicCampaignDto
	 * @param campaign
	 * @return
	 * @throws RecordNotFoundException
	 */
	private Campaign saveCampaign(BasicCampaignDto basicCampaignDto, Campaign campaign) throws RecordNotFoundException {
		Optional<Template> template = templateRepository.findById(basicCampaignDto.getTemplateId());

		if (template.isPresent()) {
			campaign.setTemplate(template.get());
		} else {
			throw new RecordNotFoundException(
					"Template not found for the given id: " + basicCampaignDto.getTemplateId());
		}

		Optional<Operator> operator = operatorRepository.findById(basicCampaignDto.getOperatorId());

		if (operator.isPresent()) {
			campaign.setOperator(operator.get());
		} else {
			throw new RecordNotFoundException(
					"Operator not found for the given id: " + basicCampaignDto.getOperatorId());
		}

		Campaign createdCampaign = campaignRepository.save(campaign);
		return createdCampaign;
	}

	/**
	 * Get Campaign by ID and return also Template and Operator fields;
	 * 
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	public Optional<Campaign> get(@NotNull Long id) throws RecordNotFoundException {

		Optional<Campaign> campaign = campaignRepository.findById(id);

		if (campaign.isPresent()) {
			return campaign;
		} else {
			throw new RecordNotFoundException("Campaign not found for the given id: " + id);
		}

	}

	/**
	 * Get the Campaign list paginated.
	 * @param page
	 * @param size
	 * @param name
	 * @return
	 */
	public Pagination<Campaign> getCampaignListPaginated(int page, int size, String name) {
		Pagination<Campaign> response = null;

		Pageable paging = PageRequest.of(page, size);

		Page<Campaign> campaignPage;

		if (name != null && !name.isEmpty()) {
			campaignPage = campaignRepository.findByNameContainingAndStatus(name, EStatus.ENABLED, paging);
		} else {
			campaignPage = campaignRepository.findByStatus(EStatus.ENABLED, paging);
		}

		if (campaignPage != null && !campaignPage.isEmpty()) {
			response = new Pagination<Campaign>(campaignPage.getTotalPages(), campaignPage.getTotalElements(),
					campaignPage.getNumber(), campaignPage.getContent());
		} else {
			response = new Pagination<Campaign>(0, 0, 0, null);
		}

		return response;
	}

	public boolean disable(@NotNull Long id) throws RecordNotFoundException {

		return true;

	}
}
