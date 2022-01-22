package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.model.Estado;
import com.devsimple.springmvc.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
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
        return estadoRepository.findAll();
    }

    @Transactional
    public Estado adicionar(Estado estado){
        return estadoRepository.save(estado);
    }
}
