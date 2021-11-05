package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.model.Cidade;
import com.devsimple.springmvc.domain.repository.CidadeRepository;
import com.devsimple.springmvc.domain.service.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private CidadeService cidadeService;
    private CidadeRepository cidadeRepository;


    @GetMapping
    public ResponseEntity<List<Cidade>> listar(){
        List<Cidade> lista = cidadeService.listar();
        return ResponseEntity.ok(lista);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@Valid @RequestBody Cidade cidade){
        return cidadeService.adicionar(cidade);
    }


    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Void> deletar(@PathVariable Long cidadeId){
        if (!cidadeRepository.existsById(cidadeId)) {
            return ResponseEntity.notFound().build();
        }

        cidadeService.remover(cidadeId);
        return ResponseEntity.noContent().build();
    }

}
