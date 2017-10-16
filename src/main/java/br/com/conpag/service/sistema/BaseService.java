package br.com.conpag.service.sistema;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import br.com.conpag.entity.exceptions.NoUserException;
import br.com.conpag.entity.sistema.Usuario;

public class BaseService {
	
	@Context
	private SecurityContext security;
	
	public Usuario getUser() throws NoUserException {
		return (Usuario) this.security.getUserPrincipal();
	}

}
