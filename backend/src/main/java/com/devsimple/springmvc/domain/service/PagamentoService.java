package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.model.Pagamento;
import com.devsimple.springmvc.domain.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> listar(){
        return pagamentoRepository.findAll();
    }

    public Pagamento adicionar(Pagamento pagamento){
        return pagamentoRepository.save(pagamento);
    }

}
