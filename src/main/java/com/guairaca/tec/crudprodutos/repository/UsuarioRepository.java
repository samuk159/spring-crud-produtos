package com.guairaca.tec.crudprodutos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guairaca.tec.crudprodutos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public Optional<Usuario> findByLogin(String login);
}
