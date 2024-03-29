package com.devsimple.springmc.domain.service;

import com.devsimple.springmc.domain.exception.ObjectNotFoundException;
import com.devsimple.springmc.domain.model.Categoria;
import com.devsimple.springmc.domain.model.Produto;
import com.devsimple.springmc.domain.repository.CategoriaRepository;
import com.devsimple.springmc.domain.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Produto buscar(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
    }

    public Page<Produto> procurar(String nome, List<Long> ids, Integer page, Integer linesPerPages, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPages, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.procurar(nome, categorias, pageRequest);
    }

    @Transactional
    public Produto adicionar(Produto produto) {
        boolean produtoEmUso = produtoRepository.findByNome(produto.getNome())
                .stream()
                .anyMatch(produtoExistente -> !produtoExistente.equals(produto));
        if (produtoEmUso) {
            throw new ObjectNotFoundException("Produto já existe");
        }
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizar(Produto produto) {
        buscar(produto.getId());
        return produtoRepository.save(produto);
    }

    @Transactional
    public void remover(Long produto) {
        produtoRepository.deleteById(produto);
    }
}
