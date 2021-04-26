package com.guairaca.tec.crudprodutos;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
		.and().authorizeRequests()
		.antMatchers("/**").permitAll()
		.and().addFilterBefore(new JWTFilter(),
                UsernamePasswordAuthenticationFilter.class);
	}
	
}
