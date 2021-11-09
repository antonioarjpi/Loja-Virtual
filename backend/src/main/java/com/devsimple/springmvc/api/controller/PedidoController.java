package com.devsimple.springmvc.api.controller;


import com.devsimple.springmvc.domain.model.Pedido;
import com.devsimple.springmvc.domain.repository.PedidoRepository;
import com.devsimple.springmvc.domain.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService pedidoservice;
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    @PostMapping
    public Pedido criarPedido(Pedido pedido){
        return pedidoservice.adicionar(pedido);
    }

}
