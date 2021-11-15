package com.devsimple.springmvc.api.dto;

import com.devsimple.springmvc.domain.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    public CategoriaDTO (Categoria categoria){
        id = categoria.getId();
        nome = categoria.getNome();
    }

}
