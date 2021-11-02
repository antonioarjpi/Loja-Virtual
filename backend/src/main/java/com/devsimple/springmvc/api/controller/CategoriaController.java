package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.repository.CategoriaRepository;
import com.devsimple.springmvc.domain.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria adicionar(@Valid @RequestBody Categoria categoria){
        return categoriaService.adicionar(categoria);
    }


}
