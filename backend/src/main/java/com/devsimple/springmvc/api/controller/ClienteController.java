package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.api.dto.ClienteDTO;
import com.devsimple.springmvc.api.dto.ClienteNewDTO;
import com.devsimple.springmvc.domain.model.Cliente;
import com.devsimple.springmvc.domain.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    @GetMapping("/completa")
    public List<Cliente> listagemCompleta(){
        return clienteService.listar();
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar(){
        List<Cliente> lista = clienteService.listar();
        List<ClienteDTO> listaDto = lista.stream()
                .map(cliente -> new ClienteDTO(cliente))
                .collect((Collectors.toList()));
        return ResponseEntity.ok().body(listaDto);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long clienteId){
        Cliente obj = clienteService.buscar(clienteId);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
        Cliente cliente = clienteService.adicionarDTO(clienteNewDTO);
        cliente = clienteService.adicionar(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{clienteId}")
    public Cliente atualizar(@Valid @RequestBody Cliente cliente,
                                          @PathVariable Long clienteId){
        cliente.setId(clienteId);
        cliente = clienteService.atualizar(cliente);
        return clienteService.atualizar(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Cliente> deletar(@PathVariable Long clienteId){
        clienteService.remover(clienteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>> listarPorPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPages,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<Cliente> lista = clienteService.buscarPagina(page, linesPerPages, orderBy, direction);
        Page<ClienteDTO> listaDto = lista.map(cliente -> new ClienteDTO(cliente));
        return ResponseEntity.ok().body(listaDto);
    }

}
