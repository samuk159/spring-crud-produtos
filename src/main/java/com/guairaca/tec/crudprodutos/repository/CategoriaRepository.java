package com.guairaca.tec.crudprodutos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guairaca.tec.crudprodutos.model.Categoria;
import com.guairaca.tec.crudprodutos.model.Produto;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	public Page<Categoria> findAllByNomeContaining(String nome, Pageable pageable);
}
