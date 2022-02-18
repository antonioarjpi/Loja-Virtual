package com.devsimple.springmc.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsimple.springmc.domain.enums.EstadoPagamento;
import com.devsimple.springmc.domain.exception.AuthorizationException;
import com.devsimple.springmc.domain.exception.ObjectNotFoundException;
import com.devsimple.springmc.domain.model.*;
import com.devsimple.springmc.domain.repository.ClienteRepository;
import com.devsimple.springmc.domain.repository.ItemPedidoRepository;
import com.devsimple.springmc.domain.repository.PagamentoRepository;
import com.devsimple.springmc.domain.repository.PedidoRepository;
import com.devsimple.springmc.domain.security.UserSS;

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

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Pedido buscar(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado"));
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

        emailService.enviarEmailConfirmacaoHtml(pedido);
        return pedido;
    }

    @Transactional
    public void remover(Long pedido){
        pedidoRepository.deleteById(pedido);
    }

    public Page<Pedido> buscarPagina(Integer page, Integer linesPerPages, String orderBy, String direction) {
        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPages, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.buscar(user.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }
}
