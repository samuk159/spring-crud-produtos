package com.guairaca.tec.crudprodutos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProdutoRepository 
	extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
	public Page<Produto> findAllByNomeContaining(String nome, Pageable pageable);
	public Page<Produto> findAllByPrecoBetween(Float valor1, Float valor2, Pageable pageable);
}
