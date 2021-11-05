package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Endereco;
import com.devsimple.springmvc.domain.repository.EnderecoRepository;
import com.devsimple.springmvc.domain.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/enderecos")
public class EnderecoController {


    private EnderecoRepository enderecoRepository;
    private EnderecoService enderecoService;

    @GetMapping
    public List<Endereco> listar(){
        return enderecoRepository.findAll();
    }

    @GetMapping("/{enderecoId}")
    public Endereco buscarId(@PathVariable Long enderecoId){
        return enderecoService.buscar(enderecoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco adicionar(@Valid @RequestBody Endereco endereco){
        return enderecoService.adicionar(endereco);
    }

    @DeleteMapping("/{enderecoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long enderecoId){
        if (!enderecoRepository.existsById(enderecoId)) {
            return ResponseEntity.notFound().build();
        }
        enderecoService.remover(enderecoId);
        return ResponseEntity.noContent().build();
    }
}
