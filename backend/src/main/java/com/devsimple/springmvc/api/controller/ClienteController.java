package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Cliente;
import com.devsimple.springmvc.domain.repository.ClienteRepository;
import com.devsimple.springmvc.domain.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {


    private ClienteRepository clienteRepository;
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @GetMapping("/{clienteId}")
    public Cliente buscarId(@PathVariable Long clienteId){
        return clienteService.buscar(clienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        return clienteService.adicionar(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deletar(@PathVariable Long clienteId){
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }
        clienteService.remover(clienteId);
        return ResponseEntity.noContent().build();
    }
}
