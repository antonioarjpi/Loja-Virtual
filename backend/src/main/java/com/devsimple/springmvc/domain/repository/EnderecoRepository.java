package com.devsimple.springmvc.domain.repository;

import com.devsimple.springmvc.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {


}
