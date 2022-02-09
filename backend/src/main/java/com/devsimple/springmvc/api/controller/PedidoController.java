package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.api.dto.CategoriaDTO;
import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.model.Pedido;
import com.devsimple.springmvc.domain.repository.PedidoRepository;
import com.devsimple.springmvc.domain.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoRepository pedidoRepository;
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<?> listar(@PathVariable Long pedidoId){
        Pedido obj = pedidoService.buscar(pedidoId);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido adicionar(@Valid @RequestBody Pedido pedido){
        return pedidoService.adicionar(pedido);
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long pedidoId){
        if (!pedidoRepository.existsById(pedidoId)) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.remover(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seuspedidos")
    public ResponseEntity<Page<Pedido>> listarPorPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPages,
            @RequestParam(value = "orderBy", defaultValue = "instante")String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC")String direction){
        Page<Pedido> lista = pedidoService.buscarPagina(page, linesPerPages, orderBy, direction);
        return ResponseEntity.ok().body(lista);
    }

}
