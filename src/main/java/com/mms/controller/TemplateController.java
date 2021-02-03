package com.mms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.dto.CreateTemplateDto;
import com.mms.dto.TemplateDto;
import com.mms.exception.RecordNotFoundException;
import com.mms.service.TemplateService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/template")
public class TemplateController {

	@Autowired
	private TemplateService service;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	public ResponseEntity<CreateTemplateDto> create(@RequestBody String createTemplateStr,
			@RequestParam List<MultipartFile> files) {
		try {

			ObjectMapper mapper = new ObjectMapper();

			CreateTemplateDto input = mapper.readValue(createTemplateStr, CreateTemplateDto.class);

			CreateTemplateDto createdTemplateVo = service.createTemplate(input, files);

			return ResponseEntity.status(HttpStatus.CREATED).body(createdTemplateVo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	public ResponseEntity<TemplateDto> update(@PathVariable String id, @RequestBody String updateTemplateStr,
			@RequestParam List<MultipartFile> files) {

		TemplateDto updatedVO;
		try {

			ObjectMapper mapper = new ObjectMapper();

			TemplateDto input = mapper.readValue(updateTemplateStr, TemplateDto.class);

			updatedVO = service.updateTemplate(input, files);
			return ResponseEntity.ok().body(updatedVO);

		} catch (RecordNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/get/{id}")
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
	@GetMapping("/getAll")
	public ResponseEntity<Map<String, Object>> getAllTemplates(@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		try {
			Map<String, Object> response = service.getTemplateListPaginated(page, size, name);

			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}
