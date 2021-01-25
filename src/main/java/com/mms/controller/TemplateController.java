package com.mms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mms.exception.RecordNotFoundException;
import com.mms.service.TemplateService;
import com.mms.vo.CreateTemplateDto;
import com.mms.vo.TemplateDto;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/template")
public class TemplateController {

	@Autowired
	private TemplateService service;
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<CreateTemplateDto> create(@RequestBody CreateTemplateDto createTemplateVO) {
		try {
			CreateTemplateDto createdTemplateVo =  service.createTemplate(createTemplateVO);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(createdTemplateVo);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/get/{id}")
	@ResponseBody
	public ResponseEntity<TemplateDto> getTemplateById(@PathVariable String id) {
		
		try {
			TemplateDto updateTemplateVO =  service.get(Long.parseLong(id));
			
			return ResponseEntity.ok().body(updateTemplateVO);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		} catch (RecordNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	@ResponseBody
	public ResponseEntity<TemplateDto> update(@PathVariable String id, @RequestBody TemplateDto updateTemplateVO) {
		
		TemplateDto updatedVO;
		try {
			
			updatedVO = service.updateTemplate(updateTemplateVO);
			return ResponseEntity.ok().body(updatedVO);
			
		} catch (RecordNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
}
