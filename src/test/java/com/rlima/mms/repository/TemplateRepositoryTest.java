package com.rlima.mms.repository;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.rlima.mms.model.Template;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TemplateRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TemplateRepository templateRepository;

	@Test
	public void whenFindByName_thenReturnTemplate() {
		// given
		Template template = new Template("Teste" + System.currentTimeMillis(), "Venha para o TIM Controle",
				"Teste da Descrição", null);
		entityManager.persist(template);
		entityManager.flush();

		// when
		Template found = templateRepository.findByName(template.getName());

		

	}
}
