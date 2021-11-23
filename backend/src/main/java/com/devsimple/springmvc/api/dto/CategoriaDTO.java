package com.devsimple.springmvc.api.dto;

import com.devsimple.springmvc.domain.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 80, message = "Mínimo 5 a 80 caracteres")
    private String nome;

    public CategoriaDTO (Categoria categoria){
        id = categoria.getId();
        nome = categoria.getNome();
    }

}
