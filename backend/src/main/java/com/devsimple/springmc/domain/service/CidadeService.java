package com.devsimple.springmc.domain.service;

import com.devsimple.springmc.domain.model.Cidade;
import com.devsimple.springmc.domain.repository.CidadeRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> buscarPorEstado(Integer estadoId){
        return cidadeRepository.findCidades(estadoId);
    }


}
