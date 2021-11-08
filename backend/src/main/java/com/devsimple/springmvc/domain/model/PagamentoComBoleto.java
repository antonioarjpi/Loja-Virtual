package com.devsimple.springmvc.domain.model;

import com.devsimple.springmvc.domain.enums.EstadoPagamaneto;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class PagamentoComBoleto extends Pagamento{
    private static final long serialVersionUID = 1L;

    private Date dataPagamento;
    private Date dataVencimento;

    public PagamentoComBoleto(Long id, EstadoPagamaneto estadoPagamaneto, Pedido pedido, Date dataPagamento, Date dataVencimento) {
        super(id, estadoPagamaneto, pedido);
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;

    }
}

