package com.devsimple.springmc.api.controller;

import com.devsimple.springmc.api.dto.CidadeDTO;
import com.devsimple.springmc.api.dto.EstadoDTO;
import com.devsimple.springmc.domain.model.Cidade;
import com.devsimple.springmc.domain.model.Estado;
import com.devsimple.springmc.domain.service.CidadeService;
import com.devsimple.springmc.domain.service.EstadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Estado Controller")
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private EstadoService estadoService;
    private CidadeService cidadeService;

    public EstadoController(EstadoService estadoService, CidadeService cidadeService) {
        this.estadoService = estadoService;
        this.cidadeService = cidadeService;
    }

    @ApiOperation("Retorna lista de estados")
    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listar() {
        List<Estado> lista = estadoService.listar();
        List<EstadoDTO> listaDTO = lista
                .stream()
                .map(estado -> new EstadoDTO(estado))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDTO);
    }

    @ApiOperation("Retorna lista de cidades pelo estado")
    @GetMapping("/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> listarCidades(@PathVariable Integer estadoId) {
        List<Cidade> lista = cidadeService.buscarPorEstado(estadoId);
        List<CidadeDTO> listaDTO = lista
                .stream()
                .map(cidade -> new CidadeDTO(cidade))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@Valid @RequestBody Estado estado) {
        return estadoService.adicionar(estado);
    }
}
