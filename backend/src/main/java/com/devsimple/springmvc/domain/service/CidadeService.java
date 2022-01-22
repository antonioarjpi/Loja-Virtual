package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.model.Cidade;
import com.devsimple.springmvc.domain.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    public Cidade adicionar(Cidade cidade){
        return cidadeRepository.save(cidade);
    }
}
