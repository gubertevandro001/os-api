package com.evandro.os.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evandro.os.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
