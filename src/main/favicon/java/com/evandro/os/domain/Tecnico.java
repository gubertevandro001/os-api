package com.evandro.os.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TECNICO")
public class Tecnico extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToMany(mappedBy="tecnico")
	private List<OS> listaOrdemServicoTecnico = new ArrayList<>();

	public Tecnico() {
	}
	
	public Tecnico(Integer id, String nome, String cpf, String telefone) {
		super(id, nome, cpf, telefone);
	}

	public List<OS> getListaOrdemServicoTecnico() {
		return listaOrdemServicoTecnico;
	}

	public void setListaOrdemServicoTecnico(List<OS> listaOrdemServicoTecnico) {
		this.listaOrdemServicoTecnico = listaOrdemServicoTecnico;
	}
	
}

