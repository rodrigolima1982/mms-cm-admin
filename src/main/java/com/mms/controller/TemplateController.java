package com.mms.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.dto.TemplateDto;
import com.mms.exception.RecordNotFoundException;
import com.mms.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mms-sender/template")
public class TemplateController {
    Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateService service;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestParam String body, @RequestParam List<MultipartFile> files) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TemplateDto input = mapper.readValue(body, TemplateDto.class);
            TemplateDto createdTemplateVo = service.createTemplate(input, files);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTemplateVo);
        } catch (Exception e) {
            logger.error("Could not create entitie ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TemplateDto> update(@PathVariable String id, @RequestParam String body, @RequestParam List<MultipartFile> files) {
        TemplateDto updatedVO;
        try {
            ObjectMapper mapper = new ObjectMapper();
            TemplateDto input = mapper.readValue(body, TemplateDto.class);
            updatedVO = service.updateTemplate(input, files);
            return ResponseEntity.ok().body(updatedVO);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<TemplateDto> getTemplateById(@PathVariable String id) {

        try {
            TemplateDto updateTemplateVO = service.get(Long.parseLong(id));

            return ResponseEntity.ok().body(updateTemplateVO);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<TemplateDto>> getAllTemplates(@RequestParam(required = false) String name,
                                                               @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            List<TemplateDto> response = service.getTemplateListPaginated(page, size, name);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> disableTemplate(@PathVariable String id) {
        try {
            service.disable(Long.parseLong(id));
            return ResponseEntity.ok(Long.parseLong(id));
        } catch (NumberFormatException | RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
