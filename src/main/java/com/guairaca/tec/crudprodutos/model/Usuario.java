package com.guairaca.tec.crudprodutos.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Usuario extends Base {

	@NotBlank
	@Size(min = 3, max = 20)
	private String login;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank
	@Size(min = 3)
	private String senha;
	
	@NotNull
	private Boolean isAdmin;
	
	@Transient
	private String token;

	public Usuario() {
		super();
	}

	public Usuario(
		@NotBlank @Size(min = 3, max = 20) String login, 
		@NotBlank @Size(min = 3) String senha,
		@NotNull Boolean isAdmin
	) {
		super();
		this.login = login;
		this.senha = senha;
		this.isAdmin = isAdmin;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
