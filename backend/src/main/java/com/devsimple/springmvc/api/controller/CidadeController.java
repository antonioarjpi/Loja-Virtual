package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.api.dto.CidadeDTO;
import com.devsimple.springmvc.domain.model.Cidade;
import com.devsimple.springmvc.domain.service.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/cidades")
public class CidadeController {

    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<CidadeDTO>> listar(){
        List<Cidade> lista = cidadeService.listar();
        List<CidadeDTO> listaDTO = lista
                .stream()
                .map(cidade -> new CidadeDTO(cidade))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@Valid @RequestBody Cidade cidade){
        return cidadeService.adicionar(cidade);
    }
}
