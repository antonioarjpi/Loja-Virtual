package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.exception.DomainException;
import com.devsimple.springmvc.domain.model.Cidade;
import com.devsimple.springmvc.domain.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Transactional
    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    @Transactional
    public Cidade buscar(Long cidadeId){
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new DomainException("cidade não encontrada"));
    }

    @Transactional
    public Cidade adicionar(Cidade cidade){
        boolean cidadeEmUso = cidadeRepository.findByNome(cidade.getNome())
                .stream()
                .anyMatch(cidadeExistente -> !cidadeExistente.equals(cidade));
        if (cidadeEmUso){
            throw new DomainException("cidade já existente");
        }
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void remover(Long cidade){
        cidadeRepository.deleteById(cidade);
    }
}
