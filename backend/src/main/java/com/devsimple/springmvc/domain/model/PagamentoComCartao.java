package com.devsimple.springmvc.domain.model;

import com.devsimple.springmvc.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    private Integer numerosParcelas;

    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Long id, EstadoPagamento estado, Pedido pedido, Integer numerosParcelas) {
        super(id, estado, pedido);
        this.numerosParcelas = numerosParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numerosParcelas;
    }

    public void setNumeroDeParcelas(Integer numerosParcelas) {
        this.numerosParcelas = numerosParcelas;


    }
}
