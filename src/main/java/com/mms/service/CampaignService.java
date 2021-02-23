package com.mms.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.ManagedBean;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.mms.dto.CampaignDto;
import com.mms.model.Campaign;
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
	 * Create a new campaign in the database.
	 * 
	 * @param campaign
	 * @return
	 */
	public CampaignDto create(@Valid CampaignDto campaignDto) throws ConstraintViolationException {

		PropertyMap<CampaignDto, Campaign> skipModifiedFieldsMap = new PropertyMap<CampaignDto, Campaign>() {
			protected void configure() {
				skip().setId(null);
			}
		};

		Campaign campaign = (Campaign) new DtoUtils().convertToCampaignEntity(new Campaign(), campaignDto,
				skipModifiedFieldsMap);

		Optional<Template> template = templateRepository.findById(campaignDto.getTemplateId());

		if (template.isPresent()) {
			campaign.setTemplate(template.get());
		}

		Optional<Operator> operator = operatorRepository.findById(campaignDto.getOperatorId());

		if (operator.isPresent()) {
			campaign.setOperator(operator.get());
		}

		return (CampaignDto) new DtoUtils().convertToDto(campaignRepository.save(campaign), new CampaignDto());

	}

	/**
	 * List campaign by startDate by date range.
	 * 
	 * @param campaignTimeStart
	 * @param campaignTimeEnd
	 * @return
	 */
	public List<Campaign> listCampaignByDateRange(Date campaignTimeStart, Date campaignTimeEnd) {

		return campaignRepository.findAllByStartDateBetween(campaignTimeStart, campaignTimeEnd);

	}
}
