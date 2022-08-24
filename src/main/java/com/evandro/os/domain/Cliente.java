package com.evandro.os.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CLIENTE")
public class Cliente extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="cliente")
	private List<OS> listaOrdemServicoCliente = new ArrayList<>();

	public Cliente() {
	}

	public Cliente(Integer id, String nome, String cpf, String telefone) {
		super(id, nome, cpf, telefone);
	}

	public List<OS> getListaOrdemServicoCliente() {
		return listaOrdemServicoCliente;
	}

	public void setListaOrdemServicoCliente(List<OS> listaOrdemServicoCliente) {
		this.listaOrdemServicoCliente = listaOrdemServicoCliente;
	}

}
