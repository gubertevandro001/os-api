package com.evandro.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evandro.os.domain.Cliente;
import com.evandro.os.domain.OS;
import com.evandro.os.domain.Tecnico;
import com.evandro.os.dtos.OSDTO;
import com.evandro.os.enums.Prioridade;
import com.evandro.os.enums.Status;
import com.evandro.os.repositorys.OSRepository;
import com.evandro.os.services.exceptions.ObjectNotFoundException;

@Service
public class OsService {
	
	@Autowired
	private OSRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public OS findById(Integer id) {
		Optional<OS> os = repository.findById(id);
		return os.orElseThrow(() -> new ObjectNotFoundException("Ordem De Serviço Não Encontrada"));
	}
	
	public List<OS> findAll() {
		return repository.findAll();
	}
	
	public OS insertOS(@Valid OSDTO osDTO) {
		return aPartirDTO(osDTO);
	}
	
	private OS aPartirDTO(OSDTO osDTO) {
		OS newOS = new OS();
		newOS.setId(osDTO.getId());
		newOS.setObservacoes(osDTO.getObservacoes());
		newOS.setPrioridade(Prioridade.toEnum(osDTO.getPrioridade()));
		newOS.setStatus(Status.toEnum(osDTO.getStatus()));
		
		Tecnico tecnico = tecnicoService.findById(osDTO.getTecnico());
		Cliente cliente = clienteService.findById(osDTO.getCliente());
		
		newOS.setTecnico(tecnico);
		newOS.setCliente(cliente);
		
		if (newOS.getStatus().getCodigo().equals(2)) {
			newOS.setDataFechamento(LocalDateTime.now());
		}
		
		return repository.save(newOS);
		
	}
	
	public OS updateOS(OSDTO osDTO) {
		findById(osDTO.getId());
		return aPartirDTO(osDTO);
	}
	
}
