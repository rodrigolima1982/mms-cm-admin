package com.mms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mms.exception.RecordNotFoundException;
import com.mms.model.Template;
import com.mms.repository.TemplateRepository;
import com.mms.util.dto.DtoUtils;
import com.mms.vo.CreateTemplateDto;
import com.mms.vo.TemplateDto;

@ManagedBean
public class TemplateService {

	@Autowired
	private TemplateRepository repository;

	public void setRepository(TemplateRepository repository) {
		this.repository = repository;
	}

	/**
	 * Create new template
	 * 
	 * @param template
	 * @return
	 */
	public CreateTemplateDto createTemplate(CreateTemplateDto createTemplateVO) {

		Template newTemplate = (Template) new DtoUtils().convertToEntity(new Template(), createTemplateVO);

		newTemplate = repository.save(newTemplate);

		return (CreateTemplateDto) new DtoUtils().convertToDto(newTemplate, createTemplateVO);
	}

	/**
	 * Update a template
	 * 
	 * @param templateVO
	 * @return
	 */
	public TemplateDto updateTemplate(TemplateDto templateVO) throws RecordNotFoundException {

		Optional<Template> existing = repository.findById(templateVO.getId());

		if (existing.isPresent()) {
			Template updatedTemplate = (Template) new DtoUtils().convertToEntity(existing.get(), templateVO);

			updatedTemplate = repository.save(updatedTemplate);

			return (TemplateDto) new DtoUtils().convertToDto(updatedTemplate, templateVO);
		} else {
			throw new RecordNotFoundException("Template not found for the given id: " + templateVO.getId());
		}

	}

	/**
	 * Return {@link Template} for a given id.
	 * 
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	public TemplateDto get(Long id) throws RecordNotFoundException {

		Optional<Template> template = repository.findById(id);

		if (!template.isPresent()) {
			throw new RecordNotFoundException("Template not found for the given id: " + id);
		}

		return (TemplateDto) new DtoUtils().convertToDto(template.get(), new TemplateDto());
	}

	/**
	 * List {@link Template} according to pagination and filter
	 * @param page
	 * @param size
	 * @param name
	 * @return
	 */
	public List<TemplateDto> getTemplateListPaginated(int page, int size, String name) {
		Pageable paging = PageRequest.of(page, size);

		Page<Template> templatePages;

		if (name != null && !name.isEmpty()) {
			templatePages = repository.findByNameContaining(name, paging);
		} else {
			templatePages = repository.findAll(paging);
		}

		List<Template> templateList = templatePages.getContent();

		List<TemplateDto> templateListVo = templateList.stream()
				.map(template -> (TemplateDto) new DtoUtils().convertToDto(template, new TemplateDto()))
				.collect(Collectors.toList());

		return templateListVo;
	}

}
