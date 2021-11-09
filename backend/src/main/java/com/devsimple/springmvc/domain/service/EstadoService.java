package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.DomainException;
import com.devsimple.springmvc.domain.model.Estado;
import com.devsimple.springmvc.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;


    @Transactional
    public Estado buscar(Long estadoId){
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new DomainException("estado não encontrada"));
    }

    @Transactional
    public Estado adicionar(Estado estado){
        boolean estadoEmUso = estadoRepository.findByNome(estado.getNome())
                .stream()
                .anyMatch(estadoExistente -> !estadoExistente.equals(estado));
        if (estadoEmUso){
            throw new DomainException("estado já existente");
        }
        return estadoRepository.save(estado);
    }

    @Transactional
    public void remover(Long estado){
        estadoRepository.deleteById(estado);
    }
}
