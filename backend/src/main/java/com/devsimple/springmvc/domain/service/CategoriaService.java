package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.api.dto.CategoriaDTO;
import com.devsimple.springmvc.domain.exception.DataIntegrityException;
import com.devsimple.springmvc.domain.exception.EntidadeNaoEncontradaException;
import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    @Transactional
    public Categoria buscar(Long categoriaId){
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Categoria não encontrada"));
    }

    @Transactional
    public Categoria adicionar(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria atualizar(Categoria categoria){
        Categoria novaCategoria = buscar(categoria.getId());
        atualizarCategoria(novaCategoria, categoria);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void remover(Long categoria){
        buscar(categoria);
        try {
            categoriaRepository.deleteById(categoria);
        }catch (DataIntegrityViolationException e) {
                throw new DataIntegrityException("Erro! Categoria possui produtos atrelados");
        }
    }

    public Page<Categoria> buscarPagina(Integer page, Integer linesPerPages, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPages, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria categoriaDto(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    private void atualizarCategoria(Categoria novaCategoria, Categoria categoria){
        novaCategoria.setNome(categoria.getNome());
    }
}
