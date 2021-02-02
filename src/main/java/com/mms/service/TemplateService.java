package com.mms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.mms.dto.CreateTemplateDto;
import com.mms.dto.TemplateDto;
import com.mms.dto.util.DtoUtils;
import com.mms.exception.DuplicatedRecordNameException;
import com.mms.exception.RecordNotFoundException;
import com.mms.model.Slide;
import com.mms.model.SlideImage;
import com.mms.model.Template;
import com.mms.repository.TemplateRepository;

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
	 * @throws IOException 
	 * @throws DuplicatedRecordNameException 
	 */
	public CreateTemplateDto createTemplate(CreateTemplateDto createTemplateVO, List<MultipartFile> slideFiles) throws DuplicatedRecordNameException, IOException {
		
		if(repository.findByName(createTemplateVO.getName())!=null) {
			
			throw new DuplicatedRecordNameException("There is another Template with the same name, please choose another name");
			
		}else {
			
			Template newTemplate = (Template) new DtoUtils().convertToEntity(new Template(), createTemplateVO);
			
			int index=0;
			
			Set<Slide> newSlide = new HashSet<Slide>();
			
			for(Slide slide : newTemplate.getSlides()) {
				slide.setImage(new SlideImage(slideFiles.get(index).getContentType(), slideFiles.get(index).getOriginalFilename(), slideFiles.get(index).getBytes()));
				newSlide.add(slide);
				index++;
			}

			newTemplate.setSlides(newSlide);
			newTemplate = repository.save(newTemplate);

			return (CreateTemplateDto) new DtoUtils().convertToDto(newTemplate, createTemplateVO);
		}
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
	public Map<String, Object> getTemplateListPaginated(int page, int size, String name) {
		Pageable paging = PageRequest.of(page, size);

		Page<Template> templatePages;
		List<TemplateDto> templateListVo=new ArrayList<TemplateDto>();

		if (name != null && !name.isEmpty()) {
			templatePages = repository.findByNameContaining(name, paging);
		} else {
			templatePages = repository.findAll(paging);
		}

		if(templatePages!=null && !templatePages.isEmpty()) {
			List<Template> templateList = templatePages.getContent();

			templateListVo = templateList.stream()
					.map(template -> (TemplateDto) new DtoUtils().convertToDto(template, new TemplateDto()))
					.collect(Collectors.toList());
		}
		
		Map<String, Object> response = new HashMap<>();
	      response.put("templates", templateListVo);
	      response.put("currentPage", templatePages.getNumber());
	      response.put("totalItems", templatePages.getTotalElements());
	      response.put("totalPages", templatePages.getTotalPages());
		

		return response;
	}

}
