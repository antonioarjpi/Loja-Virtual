package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.EntidadeNaoEncontradaException;
import com.devsimple.springmvc.domain.model.Pedido;
import com.devsimple.springmvc.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public Pedido buscar(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));
    }

    @Transactional
    public Pedido adicionar(Pedido pedido){
        boolean pedidoEmUso = pedidoRepository.findById(pedido.getId())
                .stream()
                .anyMatch(pedidoExistente -> !pedidoExistente.equals(pedido));
        if (pedidoEmUso){
            throw new EntidadeNaoEncontradaException("Pedido já existente");
        }
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void remover(Long pedido){
        pedidoRepository.deleteById(pedido);
    }
}
