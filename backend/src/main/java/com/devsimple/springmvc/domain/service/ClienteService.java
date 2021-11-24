package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.api.dto.ClienteDTO;
import com.devsimple.springmvc.domain.exception.DataIntegrityException;
import com.devsimple.springmvc.domain.exception.DomainException;
import com.devsimple.springmvc.domain.model.Cliente;
import com.devsimple.springmvc.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente buscar(Long clienteId){
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new DomainException("Cliente não encontrada"));
    }

    @Transactional
    public Cliente adicionar(Cliente cliente){
        boolean clienteEmUso = clienteRepository.findByNome(cliente.getNome())
                .stream()
                .anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
        if (clienteEmUso){
            throw new DomainException("Cliente já existente");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        Cliente novoCliente = buscar(cliente.getId());
        atualizarCliente(novoCliente, cliente);
        return clienteRepository.save(novoCliente);
    }

    @Transactional
    public void remover(Long cliente){
        buscar(cliente);
        try {
            clienteRepository.deleteById(cliente);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não pode excluir pois há pedidos para o cliente");
        }
    }

    public Page<Cliente> buscarPagina(Integer page, Integer linesPerPages, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPages, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente clienteDto(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    private void atualizarCliente(Cliente novoCliente, Cliente cliente){
        novoCliente.setNome(cliente.getNome());
        novoCliente.setEmail(cliente.getEmail());
    }
}
