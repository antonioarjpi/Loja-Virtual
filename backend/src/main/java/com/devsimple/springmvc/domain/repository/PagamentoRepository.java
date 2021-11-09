package com.devsimple.springmvc.domain.repository;

import com.devsimple.springmvc.domain.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
