package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.DomainException;
import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria adicionar(Categoria categoria){
        boolean categoriaEmUso = categoriaRepository.findByNome(categoria.getNome())
                .stream()
                .anyMatch(categoriaExistente -> !categoriaExistente.equals(categoria));
        if (categoriaEmUso){
            throw new DomainException("Categoria já existente");
        }
        return categoriaRepository.save(categoria);
    }

}
