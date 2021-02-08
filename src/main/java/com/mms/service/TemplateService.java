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

import com.mms.dto.TemplateDto;
import com.mms.exception.DuplicatedRecordNameException;
import com.mms.exception.RecordNotFoundException;
import com.mms.model.EStatus;
import com.mms.model.Slide;
import com.mms.model.SlideImage;
import com.mms.model.Template;
import com.mms.repository.TemplateRepository;
import com.mms.util.dto.DtoUtils;

@ManagedBean
public class TemplateService {

    @Autowired
    private TemplateRepository repository;

    public void setRepository(TemplateRepository repository) {
        this.repository = repository;
    }

    public TemplateDto createTemplate(TemplateDto templateDto, List<MultipartFile> slideFiles)
            throws DuplicatedRecordNameException, IOException {

        if (repository.findByName(templateDto.getName()) != null) {

            throw new DuplicatedRecordNameException(
                    "There is another Template with the same name, please choose another name");

        } else {

            Template newTemplate = (Template) new DtoUtils().convertToEntity(new Template(), templateDto);

            int index = 0;

            Set<Slide> newSlide = new HashSet<Slide>();

            for (Slide slide : newTemplate.getSlides()) {
                slide.setImage(new SlideImage(slideFiles.get(index).getContentType(),
                        slideFiles.get(index).getOriginalFilename(), slideFiles.get(index).getBytes()));
                slide.setTemplate(newTemplate);
                newSlide.add(slide);
                index++;
            }

            newTemplate.setSlides(newSlide);
            newTemplate = repository.save(newTemplate);

            return (TemplateDto) new DtoUtils().convertToDto(newTemplate, templateDto);
        }
    }

    public TemplateDto updateTemplate(TemplateDto templateDTO, List<MultipartFile> slideFiles) throws RecordNotFoundException, IOException {

        Optional<Template> existing = repository.findById(templateDTO.getId());

        if (existing.isPresent()) {
            Template updatedTemplate = (Template) new DtoUtils().convertToEntity(new Template(), templateDTO);

            int index = 0;

            Set<Slide> newSlide = new HashSet<Slide>();

            for (Slide slide : updatedTemplate.getSlides()) {
                slide.setImage(new SlideImage(slideFiles.get(index).getContentType(),
                        slideFiles.get(index).getOriginalFilename(), slideFiles.get(index).getBytes()));
                slide.setTemplate(updatedTemplate);
                newSlide.add(slide);
                index++;
            }

            updatedTemplate.setSlides(newSlide);
            updatedTemplate = repository.save(updatedTemplate);

            return (TemplateDto) new DtoUtils().convertToDto(updatedTemplate, new TemplateDto());
        } else {
            throw new RecordNotFoundException("Template not found for the given id: " + templateDTO.getId());
        }

    }

    public TemplateDto get(Long id) throws RecordNotFoundException {

        Optional<Template> template = repository.findByIdAndFetchSlidesEagerly(id);

        if (!template.isPresent()) {
            throw new RecordNotFoundException("Template not found for the given id: " + id);
        }

        return (TemplateDto) new DtoUtils().convertToDto(template.get(), new TemplateDto());
    }

    public Map<String, Object> getTemplateListPaginated(int page, int size, String name) {
        Pageable paging = PageRequest.of(page, size);

        Page<Template> templatePages;
        List<TemplateDto> templateListVo = new ArrayList<TemplateDto>();

        if (name != null && !name.isEmpty()) {
            templatePages = repository.findByNameContainingAndStatus(name, EStatus.ENABLED, paging);
        } else {
            templatePages = repository.findByStatus(EStatus.ENABLED, paging);
        }

        if (templatePages != null && !templatePages.isEmpty()) {
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


    public boolean disable(Long id) throws RecordNotFoundException {

        Optional<Template> template = repository.findById(id);

        if (!template.isPresent()) {
            throw new RecordNotFoundException("Template not found for the given id: " + id);
        } else {
            if (template.get().getCampaigns().isEmpty()) {

                repository.deleteById(id);

            } else {

                template.get().setStatus(EStatus.DISABLED);
                repository.save(template.get());
            }
        }

        return true;

    }

}
