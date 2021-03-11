package com.mms.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.mms.model.Slide;
import com.mms.model.SlideImage;
import com.mms.model.Template;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SlideRepositoryTest {
	@Autowired
	private TemplateRepository templateRepository;
	
	@Autowired
	private SlideRepository slideRepository;

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testGetSlideByTemplate_thenReturnSlide() {
		MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());
		SlideImage image;
		try {
			Template createdTemplate = new Template("Teste Template", "Template Subject", "Template Description", null, null);
			image = new SlideImage(file.getContentType(), file.getOriginalFilename(), file.getBytes());
			Slide slide = new Slide("venha para o TIM Controle", 0, image);
			slide.setTemplate(createdTemplate);
			Set<Slide> slides = new HashSet<Slide>();
			slides.add(slide);
			
			createdTemplate.setSlides(slides);
		
			

			Template newTemplate =  entityManager.persist(createdTemplate);
			entityManager.flush();
			
			newTemplate = templateRepository.getOne(newTemplate.getId());
			
			Set<Slide> createdSlides = slideRepository.findByTemplateId(newTemplate.getId().longValue());
			
			assertNotNull(createdSlides);
			assertEquals(slides.size(), createdSlides.size());
			
			for(Slide createdSlide:createdSlides) {
				assertEquals(newTemplate.getId(), createdSlide.getTemplate().getId());
			}
			
		} catch (IOException e) {
			fail(e);
		}
		
	}
}
