package com.devsimple.springmc.domain.repository;

import com.devsimple.springmc.domain.model.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {


}
