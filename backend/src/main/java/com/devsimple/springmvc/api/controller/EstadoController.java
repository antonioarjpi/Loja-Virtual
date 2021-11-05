package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Estado;
import com.devsimple.springmvc.domain.repository.EstadoRepository;
import com.devsimple.springmvc.domain.service.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private EstadoService estadoService;
    private EstadoRepository estadoRepository;


    @GetMapping
    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@Valid @RequestBody Estado estado){
        return estadoService.adicionar(estado);
    }


    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long estadoId){
        if (!estadoRepository.existsById(estadoId)) {
            return ResponseEntity.notFound().build();
        }

        estadoService.remover(estadoId);
        return ResponseEntity.noContent().build();
    }

}
