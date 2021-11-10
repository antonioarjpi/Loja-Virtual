package com.devsimple.springmvc.domain.model;

import com.devsimple.springmvc.domain.enums.EstadoPagamento;
import javax.persistence.Table;


@Table(name = "pagamento_com_cartao")
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    private Long numerosParcelas;

    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Long id, EstadoPagamento estado, Pedido pedido, Long numerosParcelas) {
        super(id, estado, pedido);
        this.numerosParcelas = numerosParcelas;
    }

    public Long getNumeroDeParcelas() {
        return numerosParcelas;
    }

    public void setNumeroDeParcelas(Long numerosParcelas) {
        this.numerosParcelas = numerosParcelas;


    }
}
