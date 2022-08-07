package com.evandro.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evandro.os.domain.Cliente;
import com.evandro.os.domain.Pessoa;
import com.evandro.os.dtos.ClienteDTO;
import com.evandro.os.repositorys.ClienteRepository;
import com.evandro.os.repositorys.PessoaRepository;
import com.evandro.os.services.exceptions.DataIntegratyViolationException;
import com.evandro.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Nenhum Cliente Encontrado!"));
	}
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente insertCliente(@Valid ClienteDTO clienteDTO) {
		return clienteRepository.save(new Cliente(null, clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getTelefone()));
	}
	
	public Cliente updateCliente(Integer id, ClienteDTO clienteDTO) {
		Cliente cliente = findById(id);
		
		if (findByCpf(clienteDTO) != null && findByCpf(clienteDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF Já Cadastrado na Base de Dados");
		}
		
		cliente.setCpf(clienteDTO.getCpf());
		cliente.setNome(clienteDTO.getNome());
		cliente.setTelefone(clienteDTO.getTelefone());
		
		return clienteRepository.save(cliente);
	}
	
	public void deleteCliente(Integer id) {
		Cliente cliente = findById(id);
		
		if (cliente.getListaOrdemServicoCliente().size() > 0) {
			throw new DataIntegratyViolationException("Cliente Possui Ordens de Serviço! Não Pode ser Deletado!");
		}
		
		clienteRepository.deleteById(id);
	}
	
	private Pessoa findByCpf(ClienteDTO clienteDTO) {
		Pessoa cliente = pessoaRepository.findByCPF(clienteDTO.getCpf());
		
		if (cliente != null) {
			return cliente;
		}
		return null;
	}
}
