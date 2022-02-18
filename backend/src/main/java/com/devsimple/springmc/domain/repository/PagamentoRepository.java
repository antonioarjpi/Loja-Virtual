package com.devsimple.springmc.domain.repository;

import com.devsimple.springmc.domain.model.Pagamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
