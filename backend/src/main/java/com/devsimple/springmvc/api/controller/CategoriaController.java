package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.api.dto.CategoriaDTO;
import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<Void> adicionar(@Valid @RequestBody CategoriaDTO categoriaDTO){
        Categoria obj = categoriaService.categoriaDto(categoriaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(categoriaDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Categoria adicionar(@Valid @RequestBody CategoriaDTO categoriaDTO){
//        Categoria obj = categoriaService.categoriaDto(categoriaDTO);
//        return categoriaService.adicionar(categoriaDTO);
//    }

    @PutMapping("/{categoriaId}")
    public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO categoriaDTO,
                                          @PathVariable Long categoriaId){
        Categoria categoria = categoriaService.categoriaDto(categoriaDTO);
        categoria.setId(categoriaId);
        categoria = categoriaService.atualizar(categoria);
        return ResponseEntity.noContent().build();
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
