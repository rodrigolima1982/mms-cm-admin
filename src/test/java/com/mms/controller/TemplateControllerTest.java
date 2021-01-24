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
import com.mms.model.Template;
import com.mms.service.TemplateService;

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

	@InjectMocks
	private TemplateController templateController;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.initMocks(this);
		
		this.template = new Template("Test Name", "Test Subject", "Test Description", null);

		this.mockMvc = MockMvcBuilders.standaloneSetup(templateController).build();
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void shouldFetchOneTemplateById() throws Exception {

		given(service.get(1L)).willReturn(template);

		this.mockMvc.perform(get("/api/template/get/{id}", 1)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is(template.getName())))
				.andExpect(jsonPath("$.subject", is(template.getSubject())));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void createTemplateAPI() throws Exception {
		given(service.createTemplate(any(Template.class))).willReturn(template);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/template/create").content(asJsonString(template))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(template.getName())))
				.andExpect(jsonPath("$.subject", is(template.getSubject())));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
