package com.guairaca.tec.crudprodutos;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.guairaca.tec.crudprodutos.service.UsuarioService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

public class JWTFilter extends GenericFilterBean {

	@Override
	public void doFilter(
		ServletRequest request, ServletResponse response, FilterChain chain
	) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if (!"OPTIONS".equals(req.getMethod()) 
			&& !req.getServletPath().startsWith("/auth")
		) {            
			String token = req.getHeader("Authorization");
			
			if (token != null) {
				try {
					String login = Jwts.parser()
							.setSigningKey(UsuarioService.CHAVE)
							.parseClaimsJws(token)
							.getBody()
							.getSubject();
					System.out.println(login);
					
					if (login != null) {
						chain.doFilter(request, response);
						return;
					}                
	            } catch (ExpiredJwtException e) {
	            	System.out.println(e.getMessage());
	            	res.setStatus(401);
					response.getWriter().println("Token expirado");
					return;
	            } catch (Exception e) {
	            	System.out.println(e.getMessage());
	            	res.setStatus(401);
					response.getWriter().println(e.getMessage());
					return;
	            }
			}
			
			res.setStatus(401);
			response.getWriter().println("Token nao informado");
		} else {
			chain.doFilter(req, res);
		}
	}

}
