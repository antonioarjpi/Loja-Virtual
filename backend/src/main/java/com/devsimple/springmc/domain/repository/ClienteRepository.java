package com.devsimple.springmc.domain.repository;

import com.devsimple.springmc.domain.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Transactional(readOnly = true)
    Cliente findByNome(String nome);

    @Transactional(readOnly = true)
    Cliente findByEmail(String email);

}
