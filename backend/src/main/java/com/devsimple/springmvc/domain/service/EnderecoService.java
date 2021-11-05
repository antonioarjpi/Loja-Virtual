package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.DomainException;
import com.devsimple.springmvc.domain.model.Endereco;
import com.devsimple.springmvc.domain.repository.EnderecoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    public Endereco create(Endereco endereco) {
        try {
            return this.enderecoRepository.save(endereco);
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Transactional
    public Endereco buscar(Long enderecoId){
        return enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new DomainException("Endereco não encontrado"));
    }

    @Transactional
    public Endereco adicionar(Endereco endereco){
        return enderecoRepository.save(endereco);
    }

    public void remover(Long enderecoId) {
        enderecoRepository.deleteById(enderecoId);
    }
}

