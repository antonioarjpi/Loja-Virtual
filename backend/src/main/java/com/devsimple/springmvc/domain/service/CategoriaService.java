package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.DomainException;
import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria buscar(Long categoriaId){
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new DomainException("Categoria não encontrada"));
    }

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

    @Transactional
    public void remover(Long categoria){
        categoriaRepository.deleteById(categoria);
    }
}
