package com.guairaca.tec.crudprodutos.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.guairaca.tec.crudprodutos.model.Usuario;
import com.guairaca.tec.crudprodutos.service.UsuarioService;

@Component
public class UsuarioSeeder {

	@Autowired
	private UsuarioService service;
	
	@EventListener
	public void seed(ContextRefreshedEvent event) {
		System.out.println("Criando usuários");
		
		try {
			service.criar(new Usuario("admin", "admin", true));
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			service.criar(new Usuario("teste", "teste", false));
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Usuários criados");
	}
	
}
