package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.EntidadeNaoEncontradaException;
import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.model.Produto;
import com.devsimple.springmvc.domain.repository.CategoriaRepository;
import com.devsimple.springmvc.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Produto buscar(Long produtoId){
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado"));
    }

    public Page<Produto> procurar(String nome, List<Long> ids, Integer page, Integer linesPerPages, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPages, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.procurar(nome, categorias, pageRequest);
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
