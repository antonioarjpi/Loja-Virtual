package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.api.dto.CategoriaDTO;
import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    @GetMapping("/produtos")
    public List<Categoria> listarCategoriaComProduto(){
        return categoriaService.listar();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar(){
        List<Categoria> lista = categoriaService.listar();
        List<CategoriaDTO> listaDto = lista.stream()
                .map(categoria -> new CategoriaDTO(categoria))
                .collect((Collectors.toList()));
        return ResponseEntity.ok().body(listaDto);
    }

    @GetMapping("/{categoriaId}")
    public ResponseEntity<?> listar(@PathVariable Long categoriaId){
        Categoria obj = categoriaService.buscar(categoriaId);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria adicionar(@Valid @RequestBody Categoria categoria){
        return categoriaService.adicionar(categoria);
    }

    @PutMapping("/{categoriaId}")
    public Categoria atualizar(@Valid @RequestBody Categoria categoria,
                                          @PathVariable Long categoriaId){
        categoria.setId(categoriaId);
        categoria = categoriaService.atualizar(categoria);
        return categoriaService.atualizar(categoria);
    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Categoria> deletar(@PathVariable Long categoriaId){
        categoriaService.remover(categoriaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> listarPorPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPages,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<Categoria> lista = categoriaService.buscarPagina(page, linesPerPages, orderBy, direction);
        Page<CategoriaDTO> listaDto = lista.map(categoria -> new CategoriaDTO(categoria));
        return ResponseEntity.ok().body(listaDto);
    }

}
