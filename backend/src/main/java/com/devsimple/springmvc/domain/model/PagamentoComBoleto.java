package com.devsimple.springmvc.domain.model;

import com.devsimple.springmvc.domain.enums.EstadoPagamaneto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "pagamento_com_boleto")
public class PagamentoComBoleto extends Pagamento{
    private static final long serialVersionUID = 1L;

    private Date dataPagamento;
    private Date dataVencimento;


}

