package com.guairaca.tec.crudprodutos.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.guairaca.tec.crudprodutos.model.Categoria;
import com.guairaca.tec.crudprodutos.repository.CategoriaRepository;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaController extends BaseController<Categoria> {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Override
	public JpaRepository<Categoria, Long> getRepository() {
		return repository;
	}
	
	@GetMapping
	public Page<Categoria> buscarTodos(
		Pageable pageable, 
		@RequestParam(required = false) String nome
	) {		
		if (nome != null && !nome.isEmpty()) {
			return repository.findAllByNomeContaining(nome, pageable);
		} else {
			return repository.findAll(pageable);
		}
	}
	
}
