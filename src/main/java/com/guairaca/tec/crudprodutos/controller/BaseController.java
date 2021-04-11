package com.guairaca.tec.crudprodutos.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;

import com.guairaca.tec.crudprodutos.model.Base;

public abstract class BaseController<Model extends Base> {
	
	public abstract JpaRepository<Model, Long> getRepository();
	
	@GetMapping("/{id}")
	public Optional<Model> buscarPorId(@PathVariable Long id) {
		return getRepository().findById(id);
	}
	
	@PostMapping
	public ResponseEntity<Model> criar(@Valid @RequestBody Model model) {
		model = getRepository().save(model);
		return ResponseEntity.status(HttpStatus.CREATED).body(model);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Model> atualizar(
		@PathVariable Long id, @Valid @RequestBody Model model
	) {
		Optional<Model> opt = getRepository().findById(id);
		
		if (opt.isPresent()) {
			model.setId(id);
			model = getRepository().save(model);
			return ResponseEntity.ok(model);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Model> excluir(@PathVariable Long id) {
		Optional<Model> opt = getRepository().findById(id);
		
		if (opt.isPresent()) {
			getRepository().deleteById(id);
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
