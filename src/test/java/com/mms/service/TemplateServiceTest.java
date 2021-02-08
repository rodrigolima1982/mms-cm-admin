package com.mms.service;

import com.mms.dto.SlideDto;
import com.mms.dto.TemplateDto;
import com.mms.exception.DuplicatedRecordNameException;
import com.mms.exception.RecordNotFoundException;
import com.mms.model.EStatus;
import com.mms.model.Slide;
import com.mms.model.SlideImage;
import com.mms.model.Template;
import com.mms.repository.TemplateRepository;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TemplateServiceTest {

	@InjectMocks
	private TemplateService service;

	@Mock
	private TemplateRepository repository;

	private Optional<Template> template;

	private TemplateDto createTemplateDto;

	private TemplateDto updateTemplateVO;

	private Template updatedTemplate;

	private Template createdTemplate;

	private Slide createdSlide;

	private SlideDto slideDto;

	private List<MultipartFile> files = new ArrayList<MultipartFile>();

	@BeforeEach
	void setUp() {

		MockitoAnnotations.initMocks(this);
		this.template = Optional.of(new Template("Test Name", "Test Subject", "Test Description", null));
		this.updatedTemplate = new Template("Updated Name", "Updated Subject", "Updated Description", null);

		MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());
		files.add(file);

		this.slideDto = new SlideDto("Venha para o TIM Controle", 30, file);
		Set<SlideDto> slides = new HashSet<SlideDto>();
		slides.add(slideDto);

		this.createTemplateDto = new TemplateDto(null, "Teste Template", "Template Subject", "Template Description",
				slides);
		this.createdTemplate = new Template("Teste Template", "Template Subject", "Template Description", null);
		this.updateTemplateVO = new TemplateDto(1L, "Updated Name", "Updated Subject", "Updated Description", slides);

		SlideImage image = null;
		createdTemplate.setSlides(new HashSet<Slide>());

		for (SlideDto slideDto : createTemplateDto.getSlides()) {
			try {
				image = new SlideImage(slideDto.getImage().getContentType(), slideDto.getImage().getOriginalFilename(),
						slideDto.getImage().getBytes());
				createdSlide = new Slide(slideDto.getText(), slideDto.getDuration(), image);
				createdTemplate.getSlides().add(createdSlide);
			} catch (IOException e) {
				fail(e);
			}
		}

	}

	@Test
	public void testGetTemplateByIdSuccess() {
		given(repository.findByIdAndFetchSlidesEagerly(1L)).willReturn(this.template);

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
			resultVoTemplate = service.updateTemplate(updateTemplateVO, files);
		} catch (RecordNotFoundException e) {
			fail(e);
		} catch (IOException e) {
			fail(e);
		}

		assertEquals("Updated Name", resultVoTemplate.getName());
		assertEquals("Updated Subject", resultVoTemplate.getSubject());
	}

	@Test
	public void testUpdateTemplateNotFound() {
		given(repository.findById(1L)).willReturn(Optional.ofNullable(null));

		try {
			service.updateTemplate(updateTemplateVO, files);
		} catch (RecordNotFoundException e) {
			assertEquals("Template not found for the given id: 1", e.getMessage());
		} catch (IOException e) {
			fail(e);
		}
	}

	@Test
	public void testGetTemplateListPaginatedWithName() {
		int page = 1;
		int size = 10;
		String name = "Test";

		Template templateOne = new Template("Test Name 01", "Test Subject 01", "Test Description 01", null);
		Template templateTwo = new Template("Test Name 02", "Test Subject 02", "Test Description 02", null);
		Template templateThree = new Template("Test Name 03", "Test Subject 03", "Test Description 03", null);

		List<Template> templateList = new ArrayList<Template>();
		templateList.add(templateOne);
		templateList.add(templateTwo);
		templateList.add(templateThree);

		Page<Template> list = new PageImpl<Template>(templateList);

		given(repository.findByNameContainingAndStatus(any(String.class), eq(EStatus.ENABLED), any(Pageable.class)))
				.willReturn(list);

		Map<String, Object> response = service.getTemplateListPaginated(page, size, name);

		@SuppressWarnings("unchecked")
		List<TemplateDto> templateDtoList = (List<TemplateDto>) response.get("templates");

		assertEquals(templateDtoList.size(), templateList.size());
		assertEquals((long) response.get("totalItems"), 3);
		verify(repository, times(1)).findByNameContainingAndStatus(any(String.class), eq(EStatus.ENABLED),
				any(Pageable.class));

	}

	@Test
	public void testGetTemplateListPaginatedWithoutName() {
		int page = 1;
		int size = 10;
		String name = null;

		Template templateOne = new Template("Test Name 01", "Test Subject 01", "Test Description 01", null);
		Template templateTwo = new Template("Test Name 02", "Test Subject 02", "Test Description 02", null);
		Template templateThree = new Template("Test Name 03", "Test Subject 03", "Test Description 03", null);

		List<Template> templateList = new ArrayList<Template>();
		templateList.add(templateOne);
		templateList.add(templateTwo);
		templateList.add(templateThree);

		Page<Template> list = new PageImpl<Template>(templateList);

		given(repository.findByStatus(eq(EStatus.ENABLED), any(Pageable.class))).willReturn(list);

		Map<String, Object> response = service.getTemplateListPaginated(page, size, name);

		@SuppressWarnings("unchecked")
		List<TemplateDto> templateDtoList = (List<TemplateDto>) response.get("templates");

		assertEquals(templateDtoList.size(), templateList.size());
		assertEquals((long) response.get("totalItems"), 3);

		verify(repository, times(1)).findByStatus(eq(EStatus.ENABLED), any(Pageable.class));

	}

	@Test
	public void testGetTemplateListPaginatedReturningEmpty() {
		int page = 1;
		int size = 10;
		String name = null;

		List<Template> templateList = new ArrayList<Template>();

		Page<Template> list = new PageImpl<Template>(templateList);

		given(repository.findByStatus(eq(EStatus.ENABLED), any(Pageable.class))).willReturn(list);

		Map<String, Object> response = service.getTemplateListPaginated(page, size, name);

		@SuppressWarnings("unchecked")
		List<TemplateDto> templateDtoList = (List<TemplateDto>) response.get("templates");

		assertEquals(templateDtoList.size(), templateList.size());
		assertEquals((long) response.get("totalItems"), 0);

		verify(repository, times(1)).findByStatus(eq(EStatus.ENABLED), any(Pageable.class));

	}

	@Test
	public void testCreateTemplate() {

		given(repository.save(any(Template.class))).willReturn(this.createdTemplate);

		TemplateDto templateDto = null;

		try {
			templateDto = service.createTemplate(this.createTemplateDto, files);
		} catch (DuplicatedRecordNameException e) {
			fail(e);
		} catch (IOException e) {
			fail(e);
		}

		assertEquals(this.createTemplateDto.getName(), templateDto.getName());

	}

	@Test
	public void testCreateTemplateWithSameName() {

		given(repository.findByName(any(String.class))).willReturn(this.createdTemplate);

		try {
			service.createTemplate(this.createTemplateDto, files);
		} catch (DuplicatedRecordNameException e) {
			assertTrue(
					e.getMessage().equals("There is another Template with the same name, please choose another name"));
		} catch (IOException e) {
			fail(e);
		}

	}
	
	@Test
	public void testDeleteTemplate() {
		fail();
	}
	
	@Test
	public void testDisableTemplate() {
		fail();
	}

}
