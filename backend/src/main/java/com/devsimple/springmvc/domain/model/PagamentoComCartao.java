package com.devsimple.springmvc.domain.model;

import com.devsimple.springmvc.domain.enums.EstadoPagamaneto;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    private Integer numerosParcelas;

    public PagamentoComCartao(){

    }

    public PagamentoComCartao(Long id, EstadoPagamaneto estadoPagamaneto, Pedido pedido, Integer numerosParcelas) {
        super(id, estadoPagamaneto, pedido);
        this.numerosParcelas = numerosParcelas;
    }
}
