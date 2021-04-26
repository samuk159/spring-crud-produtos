package com.guairaca.tec.crudprodutos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CorsFilter corsFilter;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
		
		httpSecurity.csrf().disable().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
		.and().authorizeRequests()
		.antMatchers("/**").permitAll()
		.and().addFilterBefore(new JWTFilter(),
                UsernamePasswordAuthenticationFilter.class);
	}
	
}
