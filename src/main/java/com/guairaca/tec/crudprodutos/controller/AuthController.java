package com.guairaca.tec.crudprodutos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guairaca.tec.crudprodutos.model.Usuario;
import com.guairaca.tec.crudprodutos.service.UsuarioService;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private UsuarioService service;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Usuario usuario) {
		try {
			return ResponseEntity.ok(service.login(usuario));
		} catch (NullPointerException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
