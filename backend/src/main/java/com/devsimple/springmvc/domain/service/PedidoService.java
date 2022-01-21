package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.enums.EstadoPagamento;
import com.devsimple.springmvc.domain.exception.EntidadeNaoEncontradaException;
import com.devsimple.springmvc.domain.model.ItemPedido;
import com.devsimple.springmvc.domain.model.PagamentoComBoleto;
import com.devsimple.springmvc.domain.model.Pedido;
import com.devsimple.springmvc.domain.repository.ItemPedidoRepository;
import com.devsimple.springmvc.domain.repository.PagamentoRepository;
import com.devsimple.springmvc.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@AllArgsConstructor
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Pedido buscar(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));
    }

    @Transactional
    public Pedido adicionar(Pedido pedido){
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.buscar(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        for (ItemPedido ip : pedido.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());

        emailService.enviarEmailConfirmacao(pedido);
        return pedido;
    }

    @Transactional
    public void remover(Long pedido){
        pedidoRepository.deleteById(pedido);
    }
}
