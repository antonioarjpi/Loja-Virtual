package com.devsimple.springmc.api.controller;

import com.devsimple.springmc.api.dto.ClienteDTO;
import com.devsimple.springmc.api.dto.ClienteNewDTO;
import com.devsimple.springmc.domain.model.Cliente;
import com.devsimple.springmc.domain.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Cliente Controller")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @ApiOperation("Retorna lista de clientes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        List<Cliente> lista = clienteService.listar();
        List<ClienteDTO> listaDto = lista.stream()
                .map(cliente -> new ClienteDTO(cliente))
                .collect((Collectors.toList()));
        return ResponseEntity.ok().body(listaDto);
    }

    @ApiOperation("Busca cliente pelo id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{clienteId}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long clienteId) {
        Cliente cliente = clienteService.buscar(clienteId);
        return ResponseEntity.ok().body(cliente);
    }

    @ApiOperation("Busca cliente pelo e-mail")
    @GetMapping("/email")
    public ResponseEntity<Cliente> buscaPorEmail(@RequestParam(value = "value") String email) {
        Cliente cliente = clienteService.buscarEmail(email);
        return ResponseEntity.ok().body(cliente);
    }

    @ApiOperation("Cadastro de cliente")
    @PostMapping
    public ResponseEntity<Void> adicionar(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
        Cliente cliente = clienteService.adicionarDTO(clienteNewDTO);
        cliente = clienteService.adicionar(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation("Atualização do cadastro do cliente pelo id")
    @PutMapping("/{clienteId}")
    public Cliente atualizar(@Valid @RequestBody Cliente cliente,
                             @PathVariable Long clienteId) {
        cliente.setId(clienteId);
        cliente = clienteService.atualizar(cliente);
        return clienteService.atualizar(cliente);
    }

    @ApiOperation("Exclui cadastro do cliente pelo id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Cliente> deletar(@PathVariable Long clienteId) {
        clienteService.remover(clienteId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Lista paginada de cliente")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>> listarPorPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPages,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Cliente> lista = clienteService.buscarPagina(page, linesPerPages, orderBy, direction);
        Page<ClienteDTO> listaDto = lista.map(cliente -> new ClienteDTO(cliente));
        return ResponseEntity.ok().body(listaDto);
    }

    @ApiOperation("Envia/Atuliza foto de perfil do cliente")
    @PostMapping("/picture")
    public ResponseEntity<Void> uploadFotoDePerfil(@RequestParam(name = "file") MultipartFile multipartFile) {
        URI uri = clienteService.uploadProfilePicture(multipartFile);
        return ResponseEntity.created(uri).build();
    }

}
