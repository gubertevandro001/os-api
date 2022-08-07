package com.evandro.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evandro.os.domain.Pessoa;
import com.evandro.os.domain.Tecnico;
import com.evandro.os.dtos.TecnicoDTO;
import com.evandro.os.repositorys.PessoaRepository;
import com.evandro.os.repositorys.TecnicoRepository;
import com.evandro.os.services.exceptions.DataIntegratyViolationException;
import com.evandro.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> tecnico = repository.findById(id);
		return tecnico.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFoundException("Não Existe Nenhum Técnico Cadastrado");
		}
		return repository.findAll();
	}
	
	public Tecnico insertTecnico(TecnicoDTO tecnicoDTO) {
		if (findByCPF(tecnicoDTO) != null) {
			throw new DataIntegratyViolationException("CPF Já Cadastrado na Base de Dados!");
		}
		return repository.save(new Tecnico(null, tecnicoDTO.getNome(), tecnicoDTO.getCpf(), tecnicoDTO.getTelefone()));
	}
	
	private Pessoa findByCPF(TecnicoDTO tecnicoDTO) {
		Pessoa tecnico = pessoaRepository.findByCPF(tecnicoDTO.getCpf());
		if (tecnico != null) {
			return tecnico;
		}
		return null;
	}

	public Tecnico updateTecnico(Integer id, @Valid TecnicoDTO tecnicoDTO) {
		Tecnico tecnico = findById(id);
		
		if(findByCPF(tecnicoDTO) != null && findByCPF(tecnicoDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF Já Cadastrado na Base de Dados!");
		}
		
		tecnico.setNome(tecnicoDTO.getNome());
		tecnico.setCpf(tecnicoDTO.getCpf());
		tecnico.setTelefone(tecnicoDTO.getTelefone());
		
		return repository.save(tecnico);
		
	}

	public void deleteTecnico(Integer id) {
		Tecnico tecnico = findById(id);
		if (tecnico.getListaOrdemServicoTecnico().size() > 0) {
			throw new DataIntegratyViolationException("Técnico Possui Ordens de Serviço! Não Pode ser Deletado!");
		}
		repository.deleteById(id);
	}
}

