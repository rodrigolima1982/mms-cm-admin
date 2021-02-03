package com.mms.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.mms.model.Slide;
import com.mms.model.SlideImage;
import com.mms.model.Template;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TemplateRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TemplateRepository templateRepository;

	@Test
	public void testFindById_thenReturnTemplate() {
		// given
		Template template = new Template("Test Name", "Test Subject", "Test Description", null);
		
		MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());
		SlideImage image;
		try {
			image = new SlideImage(file.getContentType(), file.getOriginalFilename(), file.getBytes());
			Slide slide = new Slide("venha para o TIM Controle", 0, image);
			slide.setTemplate(template);
			Set<Slide> slides = new HashSet<Slide>();
			slides.add(slide);
			
			template.setSlides(slides);
			entityManager.persist(template);
			entityManager.flush();

			// when
			Optional<Template> found = templateRepository.findByIdAndFetchSlidesEagerly(template.getId());

			// then
			assertEquals(template.getId(), found.get().getId());
			assertNotNull(found.get().getSlides());
			assertEquals(slides.size(), found.get().getSlides().size());
		} catch (IOException e) {
			fail(e);
		}
		
	}

	@Test
	public void testFindById_thenReturnNull() {
		// given
		Template template = new Template("Test Name", "Test Subject", "Test Description", null);
		entityManager.persist(template);
		entityManager.flush();

		// when
		Optional<Template> found = templateRepository.findById(template.getId() + 1);

		// then
		assertFalse(found.isPresent());
	}

	@Test
	public void testFindAll_thenReturnTemplatesPaginated() {
		// given
		Template template;
		for (int i = 0; i < 50; i++) {
			template = new Template("Test Name - " + i, "Test Subject" + i, "Test Description" + i, null);
			entityManager.persist(template);
			entityManager.flush();

		}

		Pageable paging = PageRequest.of(0, 10);

		// when
		Page<Template> page = templateRepository.findAll(paging);

		// then
		assertEquals(10, page.getContent().size());
		assertEquals(0, page.getNumber());
		assertEquals(50, page.getTotalElements());
		assertEquals(5, page.getTotalPages());
	}

	@Test
	public void testFindAll_thenReturnTemplatesSecondPage() {
		// given
		Template template;
		for (int i = 0; i < 50; i++) {
			template = new Template("Test Name - " + i, "Test Subject" + i, "Test Description" + i, null);
			entityManager.persist(template);
			entityManager.flush();

		}

		Pageable paging = PageRequest.of(1, 10);

		// when
		Page<Template> page = templateRepository.findAll(paging);

		// then
		assertEquals(10, page.getContent().size());
		assertEquals(1, page.getNumber());
		assertEquals(50, page.getTotalElements());
		assertEquals(5, page.getTotalPages());
	}

	@Test
	public void testFindByNameContaining_thenReturnTemplates() {
		// given
		Template template;
		for (int i = 0; i < 50; i++) {
			template = new Template("Test Name - " + i, "Test Subject - " + i, "Test Description - " + i, null);
			entityManager.persist(template);
			entityManager.flush();

		}

		Pageable paging = PageRequest.of(0, 10);

		// when
		Page<Template> page = templateRepository.findByNameContaining("Test Name -", paging);

		// then
		assertEquals(10, page.getContent().size());
		assertEquals(0, page.getNumber());
		assertEquals(50, page.getTotalElements());
		assertEquals(5, page.getTotalPages());
	}

	@Test
	public void testFindByNameContaining_thenReturnEmpty() {
		// given
		Template template;
		for (int i = 0; i < 50; i++) {
			template = new Template("Test Name - " + i, "Test Subject - " + i, "Test Description - " + i, null);
			entityManager.persist(template);
			entityManager.flush();

		}

		Pageable paging = PageRequest.of(0, 10);

		// when
		Page<Template> page = templateRepository.findByNameContaining("Invalid Name", paging);

		// then
		assertEquals(0, page.getContent().size());
		assertEquals(0, page.getNumber());
		assertEquals(0, page.getTotalElements());
		assertEquals(0, page.getTotalPages());
	}

	@Test
	public void testCreateTemplate_thenReturnTemplateDTO() {
		MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());
		SlideImage image;
		try {
			image = new SlideImage(file.getContentType(), file.getOriginalFilename(), file.getBytes());
			Slide slide = new Slide("venha para o TIM Controle", 0, image);
			Set<Slide> slides = new HashSet<Slide>();
			slides.add(slide);
			Template createdTemplate = new Template("Teste Template", "Template Subject", "Template Description", slides);

			Template newTemplate =  entityManager.persist(createdTemplate);
			entityManager.flush();

			assertNotNull(newTemplate);
			assertNotNull(newTemplate.getId());
		} catch (IOException e) {
			fail(e);
		}
		
	}
	
	@Test
	public void testCreateDuplicateTemplate_thenReturnError() {
		MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());
		SlideImage image;
		try {
			image = new SlideImage(file.getContentType(), file.getOriginalFilename(), file.getBytes());
			Slide slide = new Slide("venha para o TIM Controle", 0, image);
			Set<Slide> slides = new HashSet<Slide>();
			slides.add(slide);
			Template createdTemplate = new Template("Teste Template", "Template Subject", "Template Description", slides);
			Template createdTemplateDuplicated = new Template("Teste Template", "Template Subject", "Template Description", slides);

			entityManager.persist(createdTemplate);
			entityManager.flush();
			entityManager.persist(createdTemplateDuplicated);
			entityManager.flush();

		} catch (Throwable e) {
			assertTrue(e instanceof PersistenceException);
		}
		
	}
}
