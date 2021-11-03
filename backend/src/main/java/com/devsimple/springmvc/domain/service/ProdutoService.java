package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.DomainException;
import com.devsimple.springmvc.domain.model.Produto;
import com.devsimple.springmvc.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto buscar(Long produtoId){
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new DomainException("Produto não encontrado"));
    }

    @Transactional
    public Produto adicionar(Produto produto){
        boolean produtoEmUso = produtoRepository.findByNome(produto.getNome())
                .stream()
                .anyMatch(produtoExistente -> !produtoExistente.equals(produto));
        if (produtoEmUso){
            throw new DomainException("Produto já cadastrado");
        }
        return produtoRepository.save(produto);
    }


    public void remover(Long produtoId) {
        produtoRepository.deleteById(produtoId);
    }
}

