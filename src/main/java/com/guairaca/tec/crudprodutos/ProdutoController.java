package com.guairaca.tec.crudprodutos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		@RequestParam(required = false) Float valor1,
		@RequestParam(required = false) Float valor2
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
				
				if (valor1 != null && valor2 != null) {
					predicates.add(
						criteriaBuilder.between(root.get("preco"), valor1, valor2)
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
	public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
		produto = repository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(
		@PathVariable Long id, @RequestBody Produto produto
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
	
}
