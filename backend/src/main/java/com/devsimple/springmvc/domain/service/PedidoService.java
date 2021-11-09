package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.model.Pedido;
import com.devsimple.springmvc.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido adicionar(Pedido pedido){
    return pedidoRepository.save(pedido);
    }

}
