package com.devsimple.springmvc.domain.model;

import com.devsimple.springmvc.domain.enums.EstadoPagamaneto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pagamento_com_cartao")
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    private Integer numerosParcelas;

    public PagamentoComCartao(){

    }

}
