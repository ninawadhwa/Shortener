package com.example.demo.controller;

import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ShortnerDTO;
import com.example.demo.service.ShortnerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ShortnerController {
	
	 Logger logger = LoggerFactory.getLogger(ShortnerController.class);
	 
	@Autowired
	private ShortnerService service;
	
	@PostMapping("/shortUrl")
	public Map<String , String> saveUrl(@RequestBody ShortnerDTO dto) {
		logger.info("save url method ");
		return service.saveUrl(dto);
	}

	@GetMapping("/{url}")
	public ResponseEntity<Object> getUrl(@PathVariable String url) {
		logger.info("get short url");
		String fullUrl = service.findUrl(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(fullUrl));
		return new ResponseEntity<>(headers , HttpStatus.MOVED_PERMANENTLY);
	}
}
