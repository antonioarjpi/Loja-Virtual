package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Produto;
import com.devsimple.springmvc.domain.repository.ProdutoRepository;
import com.devsimple.springmvc.domain.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoService produtoService;
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<Produto> listar(){
        return produtoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@Valid @RequestBody Produto produto){
        return produtoService.adicionar(produto);
    }
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long produtoId){
        if (!produtoRepository.existsById(produtoId)) {
            return ResponseEntity.notFound().build();
        }
        produtoService.remover(produtoId);
        return ResponseEntity.noContent().build();
    }
}
