package com.devsimple.springmvc;

import com.devsimple.springmvc.domain.model.Categoria;
import com.devsimple.springmvc.domain.model.Produto;
import com.devsimple.springmvc.domain.repository.CategoriaRepository;
import com.devsimple.springmvc.domain.repository.ProdutoRepository;
import com.devsimple.springmvc.domain.service.CategoriaService;
import com.devsimple.springmvc.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SpringmvcApplication{


	public static void main(String[] args) {
		SpringApplication.run(SpringmvcApplication.class, args);
	}


//		Categoria cat1 = categoriaService.buscar(1L);
//		Categoria cat2 = categoriaService.buscar(2L);
//
//		Produto p1 = produtoService.buscar(1l);
//		Produto p2 = produtoService.buscar(2l);
//		Produto p3 = produtoService.buscar(3l);
//
//		cat1.getProduto().addAll(Arrays.asList(p1, p2, p3));
//		cat2.getProduto().addAll(Arrays.asList(p2));
//
//		p1.getCategoria().addAll(Arrays.asList(cat1));
//		p2.getCategoria().addAll(Arrays.asList(cat1, cat2));
//		p3.getCategoria().addAll(Arrays.asList(cat1));
//
//		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
//		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	}


