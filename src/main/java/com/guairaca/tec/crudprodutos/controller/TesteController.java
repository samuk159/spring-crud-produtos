package com.guairaca.tec.crudprodutos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/teste")
public class TesteController {

	@GetMapping
	public String teste() {
		return "A aplicação está funcionando";
	}
	
}
