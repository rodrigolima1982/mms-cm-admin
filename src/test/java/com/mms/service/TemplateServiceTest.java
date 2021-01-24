package com.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.mms.model.Template;
import com.mms.repository.TemplateRepository;

@RunWith(MockitoJUnitRunner.class)
public class TemplateServiceTest {

	@InjectMocks
	TemplateService service;
	
	@Mock
	TemplateRepository repository;
	
	Template template;
	
	@Before
    public  void init() {
        
    }
	
	@BeforeEach
	void setUp() {

		MockitoAnnotations.initMocks(this);
		this.template = new Template("Test Name", "Test Subject", "Test Description", null);

	}
	
	@Test
    public void getTemplateByIdTest()
    {
		given(repository.getOne(1L)).willReturn(this.template);
         
        Template getTemplate = service.get(1L);
         
        assertEquals("Test Name", getTemplate.getName());
        assertEquals("Test Subject", getTemplate.getSubject());
    }
}
