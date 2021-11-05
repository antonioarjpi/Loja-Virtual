package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.model.Produto;
import com.devsimple.springmvc.domain.repository.CategoriaRepository;
import com.devsimple.springmvc.domain.repository.ProdutoRepository;
import com.devsimple.springmvc.domain.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoService produtoService;
    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Produto> listar(){
        return produtoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@Valid @RequestBody Produto produto){
        return produtoService.adicionar(produto);
    }

//    @PutMapping("/{produtoId}/{categoriaId}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Produto addcategoriaToSubject(@PathVariable Long produtoId,
//                                            @PathVariable Long categoriaId
//    ) {
//        Produto produto = produtoRepository.findById(produtoId).get();
//        Categoria categoria = categoriaRepository.findById(categoriaId).get();
//        produto.enrollCategoria.add(categoria);
//        return produtoRepository.save(produto);
//    }


    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long produtoId){
        if (!produtoRepository.existsById(produtoId)) {
            return ResponseEntity.notFound().build();
        }

        produtoService.remover(produtoId);
        return ResponseEntity.noContent().build();
    }

}
