package com.evandro.os.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evandro.os.domain.Cliente;
import com.evandro.os.domain.OS;
import com.evandro.os.domain.Tecnico;
import com.evandro.os.enums.Prioridade;
import com.evandro.os.enums.Status;
import com.evandro.os.repositorys.ClienteRepository;
import com.evandro.os.repositorys.OSRepository;
import com.evandro.os.repositorys.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private OSRepository osRepository;
	
	public void instanciaDB() {
		
		Tecnico t1 = new Tecnico(null, "Evandro Gubert", "143.881.370-81", "(46) 98824-7976");
		Cliente c1 = new Cliente(null, "Carlos Emanuel", "391.857.350-86", "(46) 98854-9098");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste Create OS", Status.ANDAMENTO, t1, c1);
		
		t1.getListaOrdemServicoTecnico().add(os1);
		c1.getListaOrdemServicoCliente().add(os1);
		
		tecnicoRepository.save(t1);
		clienteRepository.save(c1);
		osRepository.save(os1);
		
		
	}

}
