package com.guairaca.tec.crudprodutos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	public Page<Produto> findAllByNomeContaining(String nome, Pageable pageable);
}
