package com.guairaca.tec.crudprodutos.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository repository;
	
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
	
	@GetMapping("/{id}")
	public Optional<Categoria> buscarPorId(@PathVariable Long id) {
		return repository.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria produto) {
		produto = repository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> atualizar(
		@PathVariable Long id, @Valid @RequestBody Categoria produto
	) {
		Optional<Categoria> opt = repository.findById(id);
		
		if (opt.isPresent()) {
			produto.setId(id);
			produto = repository.save(produto);
			return ResponseEntity.ok(produto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Categoria> excluir(@PathVariable Long id) {
		Optional<Categoria> opt = repository.findById(id);
		
		if (opt.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> trataExceptionsDeValidacao(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> erros = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String nomeDoCampo = ((FieldError) error).getField();
	        String mensagem = error.getDefaultMessage();
	        erros.put(nomeDoCampo, mensagem);
	    });
	    return erros;
	}
	
}
