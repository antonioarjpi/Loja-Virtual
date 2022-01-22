package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Estado;
import com.devsimple.springmvc.domain.service.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/estados")
public class EstadoController {

    private EstadoService estadoService;

    @GetMapping
    public List<Estado> listar(){
        return estadoService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@Valid @RequestBody Estado estado){
        return estadoService.adicionar(estado);
    }
}
