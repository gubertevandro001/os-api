package com.evandro.os.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.evandro.os.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
	
	@Query("SELECT pessoa FROM Pessoa pessoa WHERE pessoa.cpf =:cpf")
	Pessoa findByCPF(@Param("cpf") String cpf);

}
