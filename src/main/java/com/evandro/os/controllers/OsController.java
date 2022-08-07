package com.evandro.os.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.evandro.os.dtos.OSDTO;
import com.evandro.os.services.OsService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/os")
public class OsController {
	
	@Autowired
	private OsService osService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OSDTO> findById(@PathVariable Integer id) {
		OSDTO osDTO = new OSDTO(osService.findById(id));
		return ResponseEntity.ok().body(osDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<OSDTO>> findAll() {
		List<OSDTO> osSDTO = osService.findAll().stream().map(os -> new OSDTO(os)).collect(Collectors.toList());
		return ResponseEntity.ok().body(osSDTO);
	}
	
	@PostMapping
	public ResponseEntity<OSDTO> insertOS(@Valid @RequestBody OSDTO osDTO) {
		osDTO = new OSDTO(osService.insertOS(osDTO));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(osDTO.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping
	public ResponseEntity<OSDTO> updateOS(Integer id, OSDTO osDTO) {
		osDTO = new OSDTO(osService.updateOS(osDTO));
		return ResponseEntity.ok().body(osDTO);
	}

}
