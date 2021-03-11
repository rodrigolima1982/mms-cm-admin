package com.mms.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.mms.dto.BasicCampaignDto;
import com.mms.exception.RecordNotFoundException;
import com.mms.model.Campaign;
import com.mms.model.Operator;
import com.mms.model.Template;
import com.mms.repository.CampaignRepository;
import com.mms.repository.OperatorRepository;
import com.mms.repository.TemplateRepository;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceTest {

	@InjectMocks
	private CampaignService service;

	@Mock
	private CampaignRepository campaignRepository;
	private Campaign campaign;

	private BasicCampaignDto createCampaignDTO;

	@Mock
	private TemplateRepository templateRepository;
	private Optional<Template> template;

	@Mock
	private OperatorRepository operatorRepository;
	private Optional<Operator> operator;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		this.createCampaignDTO = new BasicCampaignDto("Test Campaign", 130, true, false, 1000, 1L, 1L);
		this.template = Optional.of(new Template("Test Name", "Test Subject", "Test Description", null, null));
		this.operator = Optional.of(new Operator(1L, "TIM", "Brazil"));

		this.campaign = new Campaign(1L, "Test Campaign", 130, true, false, 1000);
	}

	@Test
	public void testCreateCampaign() {
		given(templateRepository.findById(1L)).willReturn(this.template);
		given(operatorRepository.findById(1L)).willReturn(this.operator);
		given(campaignRepository.save(any(Campaign.class))).willReturn(this.campaign);

		BasicCampaignDto campaignDto = null;
		try {
			campaignDto = service.create(createCampaignDTO);
		} catch (ConstraintViolationException | RecordNotFoundException e) {
			fail();
		}

		assertEquals(createCampaignDTO.getName(), campaignDto.getName());

	}

}
