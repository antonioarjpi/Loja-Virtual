package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.repository.CategoriaRepository;
import com.devsimple.springmvc.domain.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {

    private CategoriaRepository categoriaRepository;
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
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

    @PutMapping("/{categoriasId}")
    public ResponseEntity<Void> atualizar(@RequestBody Categoria categoria,
                                          @PathVariable Long categoriaId){
        categoria.setId(categoriaId);
        categoria = categoriaService.atualizar(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Void> deletar(@PathVariable Long categoriaId){
        if (!categoriaRepository.existsById(categoriaId)) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.remover(categoriaId);
        return ResponseEntity.noContent().build();
    }

}
