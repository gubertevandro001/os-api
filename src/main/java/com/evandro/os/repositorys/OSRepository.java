package com.evandro.os.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evandro.os.domain.OS;

public interface OSRepository extends JpaRepository<OS, Integer>{

}
