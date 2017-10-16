package br.com.conpag.controller.sistema;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.conpag.dao.sistema.UsuarioDAO;
import br.com.conpag.entity.dto.sistema.SessaoAtivaDTO;

// TODO verificando com o HALLAN o que utilizar....
public class UsersOnlineController {
	
	@Inject
	private UsuarioDAO usuarioDao;
	
	//**************************************************************
	//
	// controle de sessoes ativas
	//
	//**************************************************************
	private List<HttpSession> sessoesAtivas;
	
	public HttpSession getSession( String id ){
		for( HttpSession session: this.sessoesAtivas ){
			if ( session.getId().equalsIgnoreCase(id) ){
				return session;
			}
		}
		return null;
	}

	private UsersOnlineController(){
		this.sessoesAtivas = new ArrayList<HttpSession>();
	}
	
	public void addSession( HttpSession session ){
		this.sessoesAtivas.add( session );
	}
	
	public void removeSession( HttpSession session ){
		this.sessoesAtivas.remove( session );
	}
	
	
	//*************************************************************
	//
	// metodos
	//
	//*************************************************************
	public List<SessaoAtivaDTO> getUsersOnLine(){
		List<SessaoAtivaDTO> users = new ArrayList<SessaoAtivaDTO>();
		for( HttpSession session: this.sessoesAtivas ){
			if ( session.getAttribute("user") != null ){
				users.add( new SessaoAtivaDTO( session ) );
			}
		}
		
		usuarioDao.setUltimaAcao( users );
		return users;
	}
	
	
}
