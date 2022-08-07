package com.evandro.os.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.evandro.os.domain.Tecnico;
import com.evandro.os.dtos.TecnicoDTO;
import com.evandro.os.services.TecnicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/tecnicos")
public class TecnicoController {
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO tecnicoDTO = new TecnicoDTO(tecnicoService.findById(id));
		return ResponseEntity.ok().body(tecnicoDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<Tecnico> tecnicos = tecnicoService.findAll();
		List<TecnicoDTO> tecsDTO = tecnicos.stream().map(tec -> new TecnicoDTO(tec)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(tecsDTO);
	}
	
	@PostMapping
	public ResponseEntity<TecnicoDTO> insertTecnico(@Valid @RequestBody TecnicoDTO tecDTO) {
		Tecnico tecnico = tecnicoService.insertTecnico(tecDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tecnico.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value= "/{id}")
	public ResponseEntity<TecnicoDTO> updateTecnico(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO tecnicoDTO) {
		TecnicoDTO tecDTO = new TecnicoDTO(tecnicoService.updateTecnico(id, tecnicoDTO));
		return ResponseEntity.ok().body(tecDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTecnico(@PathVariable Integer id) {
		tecnicoService.deleteTecnico(id);
		return ResponseEntity.noContent().build();
	}
}
