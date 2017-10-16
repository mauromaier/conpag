package br.com.conpag.service.sistema;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import br.com.conpag.controller.sistema.UsuarioController;
import br.com.conpag.entity.dto.sistema.UsuarioDTO;
import br.com.conpag.entity.exceptions.NoUserException;
import br.com.conpag.entity.sistema.Usuario;
import br.com.conpag.util.Criptografia;
import br.com.conpag.util.RestTypes;

@Path("/usuario")
public class UsuarioService extends BaseService{

	@EJB
	private UsuarioController controller;
	
	@Context
	private HttpServletResponse response;
	
	@Inject
	private Criptografia criptografia;
	
	@GET
	@Path("/{id}")
	@Produces( RestTypes.JSON_UTF8 )
	public Usuario getById(@PathParam("id") int id ){
		return controller.getById( id );
	}
	
	@GET
	@Path("/useronline")
	@Produces( RestTypes.JSON_UTF8 )
	public Usuario getUserSession(){
		try {
			return this.getUser();
		} catch (NoUserException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/redirect-old")
	public void redirecionaVigilantosAntigo(){
		try {
			String parameters = "parseerror";
			Usuario user = this.getUser();
			
			parameters = criptografia.encrypt( "u=" + user.getLogin() +"&p=" + user.getSenha() + "&t=" + new java.util.Date().getTime()  );
			response.sendRedirect(UsuarioController.caminho_vigilantos_antigo + parameters );
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	@GET
	@Path("/destinatarios")
	@Produces( RestTypes.JSON_UTF8 )
	public List<UsuarioDTO> getUsers(){
		try {
			return controller.selectUsersDestinatarios( this.getUser() );
		} catch (NoUserException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
