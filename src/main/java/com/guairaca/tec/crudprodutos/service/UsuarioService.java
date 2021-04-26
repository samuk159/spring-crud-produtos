package com.guairaca.tec.crudprodutos.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.guairaca.tec.crudprodutos.model.Usuario;
import com.guairaca.tec.crudprodutos.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static final String CHAVE = "12345";
	
	public Usuario criar(@Valid Usuario usuario) {
		Optional<Usuario> optional = repository.findByLogin(usuario.getLogin());
		
		if (optional.isPresent()) {
			throw new NullPointerException("Usuário já cadastrado");
		}
		
		usuario.setSenha(
			encoder.encode(usuario.getSenha())
		);
		
		return repository.save(usuario);
	}
	
	public Usuario login(Usuario usuario) {		
		Optional<Usuario> optional = repository.findByLogin(usuario.getLogin());
		
		if (optional.isEmpty()) {
			throw new NullPointerException("Usuário não cadastrado");
		}
		
		if (!encoder.matches(usuario.getSenha(), optional.get().getSenha())) {
			throw new NullPointerException("Senha incorreta");
		}
		
		usuario = optional.get();
		
		Calendar dataDeExpiracao = Calendar.getInstance();
		dataDeExpiracao.add(Calendar.DAY_OF_MONTH, 1);
		
		String token = Jwts.builder()
			.setSubject(usuario.getLogin())
			.claim("id", usuario.getId())
			.claim("isAdmin", usuario.getIsAdmin())
			.setExpiration(dataDeExpiracao.getTime())
			.setIssuedAt(new Date())
			.signWith(SignatureAlgorithm.HS512, CHAVE)
			.compact();
		
		usuario.setToken(token);
		return usuario;
	}
	
}
