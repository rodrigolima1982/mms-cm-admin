package com.mms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mms.dto.SlideDto;
import com.mms.dto.TemplateDto;
import com.mms.exception.RecordNotFoundException;
import com.mms.service.TemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TemplateController.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfiguration.class)
public class TemplateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private TemplateService service;

	private TemplateDto updateTemplateVO;

	private TemplateDto createTemplateVO;

	@InjectMocks
	private TemplateController templateController;

	MockMultipartFile file;

	private SlideDto slideDto;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.initMocks(this);

		this.updateTemplateVO = new TemplateDto(1L, "Updated Name", "Updated Subject", "Updated Description", null);

		this.file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

		this.slideDto = new SlideDto("Venha para o TIM Controle", 30, null);
		Set<SlideDto> slides = new HashSet<SlideDto>();
		slides.add(slideDto);

		this.createTemplateVO = new TemplateDto(null, "Created Name", "Created Subject", "Created Description", slides);

		this.mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();

	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void testGetById() throws Exception {

		given(service.get(1L)).willReturn(updateTemplateVO);

		this.mockMvc.perform(get("/api/template/get/{id}", 1)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is(updateTemplateVO.getName())))
				.andExpect(jsonPath("$.subject", is(updateTemplateVO.getSubject())));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testCreateTemplateSuccess() throws Exception {
		given(service.createTemplate(any(TemplateDto.class), Mockito.<MultipartFile>anyList()))
				.willReturn(this.createTemplateVO);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/template/create").file(file)
				.content(asJsonString(createTemplateVO)).contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name", is(createTemplateVO.getName())))
				.andExpect(jsonPath("$.subject", is(createTemplateVO.getSubject())));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testUpdateTemplateSuccess() throws Exception {
		given(service.updateTemplate(any(TemplateDto.class), Mockito.<MultipartFile>anyList()))
				.willReturn(updateTemplateVO);

		mockMvc.perform(
				MockMvcRequestBuilders.multipart("/api/template/update/{id}", String.valueOf(updateTemplateVO.getId()))
						.file(file).content(asJsonString(updateTemplateVO)).contentType(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(updateTemplateVO.getName())))
				.andExpect(jsonPath("$.subject", is(updateTemplateVO.getSubject())));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testUpdateTemplateNotFound() throws Exception {
		given(service.updateTemplate(any(TemplateDto.class), Mockito.<MultipartFile>anyList()))
				.willThrow(RecordNotFoundException.class);

		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/template/update/{id}", "2").file(file)
				.content(asJsonString(updateTemplateVO)).contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void testGetAll() throws Exception {

		TemplateDto templateOne = new TemplateDto(1L, "Test Name 01", "Test Subject 01", "Test Description 01", null);
		TemplateDto templateTwo = new TemplateDto(2L, "Test Name 02", "Test Subject 02", "Test Description 02", null);
		TemplateDto templateThree = new TemplateDto(3L, "Test Name 03", "Test Subject 03", "Test Description 03", null);

		List<TemplateDto> templateListDto = new ArrayList<TemplateDto>();
		templateListDto.add(templateOne);
		templateListDto.add(templateTwo);
		templateListDto.add(templateThree);

		Map<String, Object> response = new HashMap<>();
		response.put("templates", templateListDto);
		response.put("currentPage", 0);
		response.put("totalItems", 3);
		response.put("totalPages", 1);

		given(service.getTemplateListPaginated(0, 3, "name")).willReturn(response);

		this.mockMvc.perform(get("/api/template/getAll").param("size", "3").param("page", "0").param("name", "name"))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.templates.size()", is(3))).andExpect(jsonPath("$.currentPage", is(0)))
				.andExpect(jsonPath("$.totalItems", is(3))).andExpect(jsonPath("$.totalPages", is(1)));
	}
	
	@Test
	public void testDeleteTemplate() {
		fail();
	}
	
	@Test
	public void testDisableTemplate() {
		fail();
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
