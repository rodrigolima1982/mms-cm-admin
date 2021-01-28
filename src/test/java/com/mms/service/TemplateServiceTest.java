package com.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
		this.updateTemplateVO = new TemplateDto(1L, "Updated Name", "Updated Subject", "Updated Description", null);

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
	public void testGetTemplateListPaginatedWithName() {
		int page = 1;
		int size = 10;
		String name = "Test";

		Template templateOne = new Template(1L, "Test Name 01", "Test Subject 01", "Test Description 01", null);
		Template templateTwo = new Template(2L, "Test Name 02", "Test Subject 02", "Test Description 02", null);
		Template templateThree = new Template(3L, "Test Name 03", "Test Subject 03", "Test Description 03", null);

		List<Template> templateList = new ArrayList<Template>();
		templateList.add(templateOne);
		templateList.add(templateTwo);
		templateList.add(templateThree);

		Page<Template> list = new PageImpl<Template>(templateList);

		given(repository.findByNameContaining(any(String.class), any(Pageable.class))).willReturn(list);

		Map<String, Object> response = service.getTemplateListPaginated(page, size, name);
		
		@SuppressWarnings("unchecked")
		List<TemplateDto> templateDtoList = (List<TemplateDto>) response.get("templates");
		
		assertEquals(templateDtoList.size(), templateList.size());
		assertEquals((long)response.get("totalItems"), 3);
		verify(repository, times(1)).findByNameContaining(any(String.class), any(Pageable.class));

	}
	
	@Test
	public void testGetTemplateListPaginatedWithoutName() {
		int page = 1;
		int size = 10;
		String name = null;

		Template templateOne = new Template(1L, "Test Name 01", "Test Subject 01", "Test Description 01", null);
		Template templateTwo = new Template(2L, "Test Name 02", "Test Subject 02", "Test Description 02", null);
		Template templateThree = new Template(3L, "Test Name 03", "Test Subject 03", "Test Description 03", null);

		List<Template> templateList = new ArrayList<Template>();
		templateList.add(templateOne);
		templateList.add(templateTwo);
		templateList.add(templateThree);

		Page<Template> list = new PageImpl<Template>(templateList);

		given(repository.findAll(any(Pageable.class))).willReturn(list);

		Map<String, Object> response = service.getTemplateListPaginated(page, size, name);
		
		@SuppressWarnings("unchecked")
		List<TemplateDto> templateDtoList = (List<TemplateDto>) response.get("templates");
		
		assertEquals(templateDtoList.size(), templateList.size());
		assertEquals((long)response.get("totalItems"), 3);
		
		verify(repository, times(1)).findAll(any(Pageable.class));

	}
	
	@Test
	public void testGetTemplateListPaginatedReturningEmpty() {
		int page = 1;
		int size = 10;
		String name = null;


		List<Template> templateList = new ArrayList<Template>();

		Page<Template> list = new PageImpl<Template>(templateList);

		given(repository.findAll(any(Pageable.class))).willReturn(list);

		Map<String, Object> response = service.getTemplateListPaginated(page, size, name);
		
		@SuppressWarnings("unchecked")
		List<TemplateDto> templateDtoList = (List<TemplateDto>) response.get("templates");
		
		assertEquals(templateDtoList.size(), templateList.size());
		assertEquals((long)response.get("totalItems"), 0);
		
		verify(repository, times(1)).findAll(any(Pageable.class));

	}
	
	

}
