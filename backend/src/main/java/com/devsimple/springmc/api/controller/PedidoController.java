package com.devsimple.springmc.api.controller;

import com.devsimple.springmc.domain.model.Pedido;
import com.devsimple.springmc.domain.repository.PedidoRepository;
import com.devsimple.springmc.domain.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Pedido Controller")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoRepository pedidoRepository;
    private PedidoService pedidoService;

    public PedidoController(PedidoRepository pedidoRepository, PedidoService pedidoService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
    }

    @ApiOperation("Lista todos os pedidos")
    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @ApiOperation("Busca pedido pelo id")
    @GetMapping("/{pedidoId}")
    public ResponseEntity<?> listar(@PathVariable Long pedidoId) {
        Pedido obj = pedidoService.buscar(pedidoId);
        return ResponseEntity.ok().body(obj);
    }

    @ApiOperation("Cria um pedido")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido adicionar(@Valid @RequestBody Pedido pedido) {
        return pedidoService.adicionar(pedido);
    }

    @ApiOperation("Exclui um pedido")
    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long pedidoId) {
        if (!pedidoRepository.existsById(pedidoId)) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.remover(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("/Lista paginada dos pedidos por cliente")
    @GetMapping("/seuspedidos")
    public ResponseEntity<Page<Pedido>> listarPorPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPages,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Pedido> lista = pedidoService.buscarPagina(page, linesPerPages, orderBy, direction);
        return ResponseEntity.ok().body(lista);
    }

}
