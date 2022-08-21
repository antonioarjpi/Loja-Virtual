package com.devsimple.springmc.domain.service;

import com.devsimple.springmc.domain.model.Cidade;
import com.devsimple.springmc.domain.repository.CidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public List<Cidade> buscarPorEstado(Integer estadoId) {
        return cidadeRepository.findCidades(estadoId);
    }
}
