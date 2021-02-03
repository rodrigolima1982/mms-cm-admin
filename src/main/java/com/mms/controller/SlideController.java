package com.mms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mms.dto.SlideDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/slide")
public class SlideController {
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/get/bytemplate")
	@ResponseBody
	public ResponseEntity<SlideDTO> getTemplateById(@RequestParam String templateId) {

		
		return null;
	}

}