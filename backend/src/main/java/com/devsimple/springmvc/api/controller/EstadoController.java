package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.api.dto.CidadeDTO;
import com.devsimple.springmvc.api.dto.EstadoDTO;
import com.devsimple.springmvc.domain.model.Cidade;
import com.devsimple.springmvc.domain.model.Estado;
import com.devsimple.springmvc.domain.service.CidadeService;
import com.devsimple.springmvc.domain.service.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/estados")
public class EstadoController {

    private EstadoService estadoService;
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listar(){
        List<Estado> lista = estadoService.listar();
        List<EstadoDTO> listaDTO = lista
                .stream()
                .map(estado -> new EstadoDTO(estado))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDTO);
    }

    @GetMapping("/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> listarCidades(@PathVariable Integer estadoId){
        List<Cidade> lista = cidadeService.listarPeloEstado(estadoId);
        List<CidadeDTO> listaDTO = lista
                .stream()
                .map(cidade -> new CidadeDTO(cidade))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@Valid @RequestBody Estado estado){
        return estadoService.adicionar(estado);
    }
}
