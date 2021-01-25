package com.mms.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.exception.RecordNotFoundException;
import com.mms.model.Template;
import com.mms.service.TemplateService;
import com.mms.vo.CreateTemplateDto;
import com.mms.vo.TemplateDto;

@WebMvcTest(controllers = TemplateController.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfiguration.class)
public class TemplateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private TemplateService service;

	private Template template;
	
	private TemplateDto updateTemplateVO;
	
	private CreateTemplateDto createTemplateVO;

	@InjectMocks
	private TemplateController templateController;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.initMocks(this);
		
		this.template = new Template(1L, "Test Name", "Test Subject", "Test Description", null);
		
		this.updateTemplateVO = new TemplateDto(1L, "Updated Name", "Updated Subject", "Updated Description", null);
		
		this.createTemplateVO = new CreateTemplateDto(1L, "Created Name", "Created Subject", "Created Description", null);

		this.mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void shouldFetchOneTemplateById() throws Exception {

		given(service.get(1L)).willReturn(updateTemplateVO);

		this.mockMvc.perform(get("/api/template/get/{id}", 1)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is(updateTemplateVO.getName())))
				.andExpect(jsonPath("$.subject", is(updateTemplateVO.getSubject())));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testCreateTemplateSuccess() throws Exception {
		given(service.createTemplate(any(CreateTemplateDto.class))).willReturn(this.createTemplateVO);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/template/create").content(asJsonString(template))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(createTemplateVO.getName())))
				.andExpect(jsonPath("$.subject", is(createTemplateVO.getSubject())));
	}
	
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testUpdateTemplateSuccess() throws Exception {
		given(service.updateTemplate(any(TemplateDto.class))).willReturn(updateTemplateVO);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/template/update/{id}", String.valueOf(updateTemplateVO.getId())).content(asJsonString(updateTemplateVO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(updateTemplateVO.getName())))
				.andExpect(jsonPath("$.subject", is(updateTemplateVO.getSubject())));
	}
	
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testUpdateTemplateNotFound() throws Exception {
		given(service.updateTemplate(any(TemplateDto.class))).willThrow(RecordNotFoundException.class);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/template/update/{id}", "2").content(asJsonString(updateTemplateVO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isNotFound())
				.andExpect(jsonPath("$").doesNotExist());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
