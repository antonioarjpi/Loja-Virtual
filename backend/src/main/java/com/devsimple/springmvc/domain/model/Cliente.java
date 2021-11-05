package com.devsimple.springmvc.domain.model;

import com.devsimple.springmvc.domain.enums.TipoCliente;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nome;
    private String email;

    @Column(name = "cpf")
    private String cpfOuCnpj;

    @Column(name = "tipo")
    private TipoCliente tipoCliente;

    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "telefone")
    private Set<String> telefones = new HashSet<>();

//    public Cliente(Long id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, List<Endereco> enderecos, Set<String> telefones) {
//        Id = id;
//        this.nome = nome;
//        this.email = email;
//        this.cpfOuCnpj = cpfOuCnpj;
//        this.tipo = tipo.getCod();
//        this.enderecos = enderecos;
//        this.telefones = telefones;
//    }

//    public TipoCliente getTipo() {
//        return TipoCliente.toEnum(tipo.longValue());
//    }
//
//    public void setTipo(TipoCliente tipo) {
//        this.tipo = tipo.getCod();
//    }
}
