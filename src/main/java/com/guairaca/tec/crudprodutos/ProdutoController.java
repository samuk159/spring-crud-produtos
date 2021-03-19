package com.guairaca.tec.crudprodutos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping(path = "/buscar-todos")
	public List<Produto> buscarTodos() {
		return repository.findAll();
	}
	
}
