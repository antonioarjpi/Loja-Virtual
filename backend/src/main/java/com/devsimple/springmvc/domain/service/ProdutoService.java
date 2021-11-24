package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.EntidadeNaoEncontradaException;
import com.devsimple.springmvc.domain.model.Produto;
import com.devsimple.springmvc.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto buscar(Long produtoId){
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado"));
    }

    @Transactional
    public Produto adicionar(Produto produto){
        boolean produtoEmUso = produtoRepository.findByNome(produto.getNome())
                .stream()
                .anyMatch(produtoExistente -> !produtoExistente.equals(produto));
        if (produtoEmUso){
            throw new EntidadeNaoEncontradaException("Produto já existente");
        }
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizar(Produto produto){
        buscar(produto.getId());
        return produtoRepository.save(produto);
    }

    @Transactional
    public void remover(Long produto){
        produtoRepository.deleteById(produto);
    }
}
