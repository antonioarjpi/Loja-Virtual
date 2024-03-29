package com.devsimple.springmc.api.controller;

import com.devsimple.springmc.api.dto.ProdutoDTO;
import com.devsimple.springmc.api.utils.URL;
import com.devsimple.springmc.domain.model.Produto;
import com.devsimple.springmc.domain.repository.ProdutoRepository;
import com.devsimple.springmc.domain.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Produto Controller")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;
    private ProdutoService produtoService;

    public ProdutoController(ProdutoRepository produtoRepository, ProdutoService produtoService) {
        this.produtoRepository = produtoRepository;
        this.produtoService = produtoService;
    }

    @ApiOperation("Lista todos os produtos")
    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @ApiOperation("Busca pedido pelo id")
    @GetMapping("/{produtoId}")
    public ResponseEntity<?> listar(@PathVariable Long produtoId) {
        Produto obj = produtoService.buscar(produtoId);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation("Cadastro de produto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@Valid @RequestBody Produto produto) {
        return produtoService.adicionar(produto);
    }

    @ApiOperation("Atualiza produto")
    @PutMapping("/{produtoId}")
    public Produto atualizar(@Valid @RequestBody Produto produto,
                             @PathVariable Long produtoId) {
        produto.setId(produtoId);
        produto = produtoService.atualizar(produto);
        return produtoService.atualizar(produto);
    }

    @ApiOperation("Deleta produto pelo id")
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long produtoId) {
        produtoService.remover(produtoId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Retorna lista paginada de produtos")
    @GetMapping("/page")
    public ResponseEntity<Page<ProdutoDTO>> listarPorPagina(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Long> ids = URL.decodeIntList(categorias);
        Page<Produto> list = produtoService.procurar(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = list.map(produto -> new ProdutoDTO(produto));
        return ResponseEntity.ok().body(listDto);
    }

}
