package com.guairaca.tec.crudprodutos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

import com.guairaca.tec.crudprodutos.model.Produto;
import com.guairaca.tec.crudprodutos.repository.ProdutoRepository;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;

	@GetMapping(path = "/teste")
	public Produto testeProdutos() {
		Produto produto = new Produto();
		produto.setNome("Tênis");
		produto.setPreco(200f);
		
		return repository.save(produto);
	}
	
	@GetMapping
	public Page<Produto> buscarTodos(
		Pageable pageable, 
		@RequestParam(required = false) String nome,
		@RequestParam(required = false) Float precoMin,
		@RequestParam(required = false) Float precoMax
	) {
		Specification<Produto> specification = new Specification<Produto>() {
			@Override
			public Predicate toPredicate(
				Root<Produto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder
			) {
				List<Predicate> predicates = new ArrayList<>();
				
				if (nome != null) {
					predicates.add(
						criteriaBuilder.like(root.get("nome"), "%" + nome + "%")
					);
				}
				
				if (precoMin != null) {
					predicates.add(
						criteriaBuilder.greaterThanOrEqualTo(
							root.get("preco"), precoMin
						)
					);
				}
				
				if (precoMax != null) {
					predicates.add(
						criteriaBuilder.lessThanOrEqualTo(
							root.get("preco"), precoMax
						)
					);
				}
				
				return criteriaBuilder.and(predicates.toArray(
					new Predicate[predicates.size()]
				));
			}
		};
		
		return repository.findAll(specification, pageable);
	}
	
	@GetMapping("/{id}")
	public Optional<Produto> buscarPorId(@PathVariable Long id) {
		return repository.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
		produto = repository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(
		@PathVariable Long id, @Valid @RequestBody Produto produto
	) {
		Optional<Produto> opt = repository.findById(id);
		
		if (opt.isPresent()) {
			produto.setId(id);
			produto = repository.save(produto);
			return ResponseEntity.ok(produto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Produto> excluir(@PathVariable Long id) {
		Optional<Produto> opt = repository.findById(id);
		
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
