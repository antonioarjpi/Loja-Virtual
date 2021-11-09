package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.DomainException;
import com.devsimple.springmvc.domain.model.Cliente;
import com.devsimple.springmvc.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente create(Cliente cliente) {
        try {
            return this.clienteRepository.save(cliente);
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Transactional
    public Cliente buscar(Long clienteId){
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new DomainException("Cliente não encontrado"));
    }

    @Transactional
    public Cliente adicionar(Cliente cliente){
        boolean clienteEmUso = clienteRepository.findByNome(cliente.getCpfOuCnpj())
                .stream()
                .anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
        if (clienteEmUso){
            throw new DomainException("Cliente já cadastrado");
        }
        return clienteRepository.save(cliente);
    }

    public void remover(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}

