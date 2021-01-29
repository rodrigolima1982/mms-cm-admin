package com.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

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
		entityManager.persist(template);
		entityManager.flush();

		// when
		Optional<Template> found = templateRepository.findById(template.getId());

		// then
		assertEquals(found.get().getId(), template.getId());
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
		Page<Template>page = templateRepository.findAll(paging);

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
		Page<Template>page = templateRepository.findAll(paging);

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
		Page<Template>page = templateRepository.findByNameContaining("Test Name -", paging);

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
		Page<Template>page = templateRepository.findByNameContaining("Invalid Name", paging);

		// then
		assertEquals(0, page.getContent().size());
		assertEquals(0, page.getNumber());
		assertEquals(0, page.getTotalElements());
		assertEquals(0, page.getTotalPages());
	}
}
