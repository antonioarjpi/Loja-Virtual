package com.devsimple.springmc.domain.service;

import com.devsimple.springmc.domain.model.Estado;
import com.devsimple.springmc.domain.repository.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public List<Estado> listar(){
        return estadoRepository.findAllByOrderByNome();
    }

    @Transactional
    public Estado adicionar(Estado estado){
        return estadoRepository.save(estado);
    }
}
