package br.com.conpag.controller.sistema;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.com.conpag.controller.BaseController;
import br.com.conpag.dao.sistema.UsuarioDAO;
import br.com.conpag.entity.dto.sistema.UsuarioDTO;
import br.com.conpag.entity.sistema.Usuario;
import br.com.conpag.util.PasswordUtils;
import br.com.conpag.util.mail.Email;
import br.com.conpag.util.mail.EmailSender;
import br.com.conpag.util.mail.EmailUtils;

@Stateless
public class UsuarioController extends BaseController<Usuario>{
	
	/*
	 * CAMINHO HOMOLOGACAO
	 */
//	public static final String caminho_vigilantos_antigo = "http://homologacao.dive.sc.gov.br:8080/vigilantos3?token=";
	
	/* 
	 * CAMINHO DO LOCALHOST
	 */ 
	//public static final String caminho_vigilantos_antigo = "http://localhost:9090/vigilantos3?token=";
	 
	
	/* 
	 * CAMINHO PRODUCAO
	 */
	 public static final String caminho_vigilantos_antigo = "http://vigilantos3.dive.sc.gov.br/vigilantos3?token="; 

	
	@Inject
	private PasswordUtils passwordUtils;
	
	@Inject
	private UsuarioDAO usuarioDao;
	
	@Inject
	private EmailUtils emailUtils;
	
	@EJB
	private EmailSender emailSender;
	
	public void insert( Usuario user ){
		this.update( user );
	}
	
	public boolean updateSenha( Usuario user, String senha ){
		String novaSenha = passwordUtils.encript(senha);
		user.setSenha(novaSenha);
		user.setExpirado(false);
		
		this.update( user );
		return true;
	}

	// TODO verificar se esta sendo alterado a SENHA
	public void updateSenhaNoEncrypt( Usuario user, String senha ){
		Query q = this.em.createNamedQuery( Usuario.JPQL_UPDATE_SENHA );
		q.setParameter( "senha", senha );
		q.setParameter( "id", user.getId() );
		q.executeUpdate();
	}
	
	public Usuario doLogin( String login_form, String senha_form ){
		
		login_form = login_form.replace(';', ' ');
        login_form = login_form.replace('\'', ' '); 
        login_form = login_form.replace('\"', ' ');
        
        senha_form = senha_form.replace(';', ' ');
        senha_form = senha_form.replace('\'', ' ');
        senha_form = senha_form.replace('\"', ' ');
		
        String senha = passwordUtils.encript( senha_form );
		
		return this.doLoginSSO( login_form, senha );
	}
	
	
	/**
	 * Metodo utilizado para transicao entre o vigilantos antigo e o novo
	 * compara a senha no banco ja criptografada
	 * @param login o login do usuario
	 * @param senha a senha, ja criptografada
	 * @return o usuario
	 */
	public Usuario doLoginSSO( String login, String senha ){
		Query q = this.em.createNamedQuery( Usuario.JPQL_DO_LOGIN );
		q.setParameter( "login", login );
		q.setParameter( "senha", senha );

		if( ! q.getResultList().isEmpty() ){
			Object o = q.getSingleResult();
			if ( o != null ){
				Usuario u = (Usuario)o;
				return u;
			}	
		}

		return null;
	}
	
	public Usuario getByLogin( String login ){
		Query q = this.em.createNamedQuery( Usuario.JPQL_FIND_BY_LOGIN );
		q.setParameter( "login", login );

		Object o = q.getSingleResult();
		if ( o != null ){
			Usuario u = (Usuario)o;
			return u;
		}
		return null;
	}
	
	
	public String geraNovaSenha( String login_form ){
		String senha = passwordUtils.geraSenhaTop();
		String novaSenha = passwordUtils.encript(senha);
		
		String email = usuarioDao.recuperaSenha(login_form, novaSenha);
		
		if ( email != null && !email.equalsIgnoreCase("NOUSER") ){
			if ( !email.equalsIgnoreCase("") ){
				//mandar email
				Email mail = emailUtils.generateMailRecuperaSenha( email, login_form, senha );
				
				emailSender.sendEmails( mail );

				return "Uma nova senha foi enviada ao email cadastrado no sistema.";
			}
			else{
				//avisa que nao ha email cadastrado
				return "Seu usuário não possui e-mail cadastrado.\nFavor entrar com contato com os administradores do sistema.";
			}
		}
		else{
			//avisa que nao ha usuario cadastrado.
			return "Não foi encontrado um usuário com os dados informados.";
		}
	}
	
	public List<UsuarioDTO> selectAllDTO(){
		return usuarioDao.getUsers( null, 0 );
	}
	
	public List<UsuarioDTO> selectUsersDestinatarios( Usuario logado ){
		return usuarioDao.getDestinatariosMensagens(logado);
	}
	
	public List<UsuarioDTO> selectDTO( String nomelogin, int idModulo, boolean acesso ){
		List<UsuarioDTO> lista = usuarioDao.getUsers( nomelogin, idModulo );
		if ( acesso ){
			usuarioDao.setUltimoAcesso( lista );
		}
		return lista;
	}
	
	/**************************
	 * 
	 * Queries para configurar a utilizacao do novo sistema (flex)
	 * 
	 ***************************/
	public boolean getNovoSistema( int idUser ){
		return usuarioDao.getNovoSistema(idUser);
	}

	public boolean setNovoSistema( int idUser, boolean novo ){
		return usuarioDao.setNovoSistema(idUser, novo);
	}
}
