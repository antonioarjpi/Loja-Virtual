package com.devsimple.springmvc.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoPagamaneto {

    PENDENTE (1, "Pendente"),
    QUITADO (2, "Quitado"),
    CANCELADO (3, "Cancelado");

    private int cod;
    private String descricao;

    public static EstadoPagamaneto toEnum(Long cod){
        if (cod==null){
            return null;
        }
        for (EstadoPagamaneto x : EstadoPagamaneto.values()){
            if ((cod.equals(x.getCod()))){
                return x;
            }
        }
        throw new IllegalArgumentException("Código errado"+ cod);
    }
}
