package com.devsimple.springmvc.api.dto;

import com.devsimple.springmvc.domain.model.Cliente;
import com.devsimple.springmvc.domain.service.validations.ClienteUpdate;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ClienteUpdate
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String email;

    public ClienteDTO(Cliente cliente){
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
    }
}
