package com.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.mms.exception.RecordNotFoundException;
import com.mms.model.Template;
import com.mms.repository.TemplateRepository;
import com.mms.vo.TemplateDto;

@RunWith(MockitoJUnitRunner.class)
public class TemplateServiceTest {

	@InjectMocks
	private TemplateService service;

	@Mock
	private TemplateRepository repository;

	private Optional<Template> template;

	private TemplateDto updateTemplateVO;

	private Template updatedTemplate;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.initMocks(this);
		this.template = Optional.of(new Template(1L, "Test Name", "Test Subject", "Test Description", null));
		this.updatedTemplate = new Template(1L, "Updated Name", "Updated Subject", "Updated Description", null);
		this.updateTemplateVO = new TemplateDto(1L, "Updated Name", "Updated Subject", "Updated Description",
				null);

	}

	@Test
	public void testGetTemplateByIdSuccess() {
		given(repository.findById(1L)).willReturn(this.template);

		TemplateDto getTemplate = null;
		try {
			getTemplate = service.get(1L);
		} catch (RecordNotFoundException e) {
			fail();
			e.printStackTrace();
		}

		assertEquals("Test Name", getTemplate.getName());
		assertEquals("Test Subject", getTemplate.getSubject());
	}

	@Test
	public void testGetTemplateByIdNotFound() {
		given(repository.findById(2L)).willReturn(Optional.ofNullable(null));

		try {
			service.get(2L);
		} catch (RecordNotFoundException e) {
			assertEquals("Template not found for the given id: 2", e.getMessage());
		}
	}

	@Test
	public void testUpdateTemplate() {
		given(repository.findById(1L)).willReturn(this.template);

		given(repository.save(any(Template.class))).willReturn(this.updatedTemplate);

		TemplateDto resultVoTemplate = null;
		try {
			resultVoTemplate = service.updateTemplate(updateTemplateVO);
		} catch (RecordNotFoundException e) {
			fail();
			e.printStackTrace();
		}

		assertEquals("Updated Name", resultVoTemplate.getName());
		assertEquals("Updated Subject", resultVoTemplate.getSubject());
	}

	@Test
	public void testUpdateTemplateNotFound() {
		given(repository.findById(1L)).willReturn(Optional.ofNullable(null));

		try {
			service.updateTemplate(updateTemplateVO);
		} catch (RecordNotFoundException e) {
			assertEquals("Template not found for the given id: 1", e.getMessage());
		}
	}
	
	@Test
	public void testListTemplateSuccess() {
		fail();
	}
	
	
}
