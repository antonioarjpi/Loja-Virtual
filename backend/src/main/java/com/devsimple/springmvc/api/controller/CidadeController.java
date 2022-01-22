package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Cidade;
import com.devsimple.springmvc.domain.service.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cidades")
public class CidadeController {

    private CidadeService cidadeService;

    @GetMapping
    public List<Cidade> listar(){
        return cidadeService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@Valid @RequestBody Cidade cidade){
        return cidadeService.adicionar(cidade);
    }
}
