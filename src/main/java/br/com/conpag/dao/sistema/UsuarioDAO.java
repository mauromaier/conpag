package br.com.conpag.dao.sistema;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.dao.BaseDAO;
import br.com.conpag.entity.dto.sistema.SessaoAtivaDTO;
import br.com.conpag.entity.dto.sistema.UsuarioDTO;
import br.com.conpag.entity.sistema.Role;
import br.com.conpag.entity.sistema.Usuario;

public class UsuarioDAO extends BaseDAO{

	private UsuarioDAO(){
	}
	
	private static final String SQL_SELECT_ID_BY_LOGIN = "usuario.selectIdByLogin";
	private static final String SQL_SET_NEW_PASSWORD = "usuario.setNewPassword";
	
	private static final String SQL_SELECT_ADMINS_BY_MODULO = "usuario.dto.getAdminsByModulos";
	private static final String SQL_SELECT_BY_MODULOS = "usuario.dto.getByModulos";
	
	@Inject
	private LogController logController;
	
	/**
	 * Faz o update da senha e expira o usuario, atraves de seu login
	 * @param login o login do usuario
	 * @param novaSenha a nova senha
	 * @return o email do usuario
	 */
	public String recuperaSenha( String login, String novaSenha ){
		String email = null;
		if (login == null){
			return null;
		}

		try{
			String queryGetId = this.queryManager.getQuery( SQL_SELECT_ID_BY_LOGIN );
			PreparedStatement psGetId = this.connection.prepareStatement( queryGetId );
			
			String queryUpdate = this.queryManager.getQuery( SQL_SET_NEW_PASSWORD );
			PreparedStatement psUpdate = this.connection.prepareStatement( queryUpdate );
		
			psGetId.setString(1, login);
			ResultSet rs = psGetId.executeQuery();
			int id = -1;
			if (rs.next()){
				id = rs.getInt("id_user");
				email = rs.getString("email");
			}
			if (id == -1){
				return "NOUSER";
			}
			psUpdate.setString(1, novaSenha);
			psUpdate.setInt(2, id);
			psUpdate.execute();
			return email;
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}
	
	public List<UsuarioDTO> getUsers( String nomelogin, int idModulo ){
		List<UsuarioDTO> retorno = new ArrayList<UsuarioDTO>();
		PreparedStatement ps = null;

		try {
			if ( nomelogin != null && !nomelogin.equalsIgnoreCase("") && idModulo > 0 ){
				String query = this.queryManager.getQuery( "usuario.dto.getByNomeModulo" );
				ps = this.connection.prepareStatement(query);
				ps.setString(1, String.format( "%%%s%%" , nomelogin));
				ps.setString(2, String.format( "%%%s%%" , nomelogin));
				ps.setInt(3, idModulo );
			}
			else if ( nomelogin != null && !nomelogin.equalsIgnoreCase("") ){
				String query = this.queryManager.getQuery( "usuario.dto.getByNome" );
				ps = this.connection.prepareStatement( query );
				ps.setString(1, String.format( "%%%s%%" , nomelogin));
				ps.setString(2, String.format( "%%%s%%" , nomelogin));
			}
			else if ( idModulo > 0 ){
				String query = this.queryManager.getQuery( "usuario.dto.getByModulo" );
				ps = this.connection.prepareStatement( query );
				ps.setInt(1, idModulo );
			}
			else{
				String query = this.queryManager.getQuery( "usuario.dto.getAll" );
				ps = this.connection.prepareStatement( query );
			}
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retorno.add( this.createEntity(rs) );
			}
		} catch (Exception e) {
			logController.printLog(e, true);
		} 
		return retorno;
	}
	
	
	public List<UsuarioDTO> getDestinatariosMensagens(Usuario userLogado){
		List<UsuarioDTO> retorno = new ArrayList<UsuarioDTO>();
		PreparedStatement ps = null;
		try {
			//se for admin de algum modulo, permite enviar mensagens a todos os usuarios
			//dos modulos
			boolean somenteAdmin = true;
			for (  Role r : userLogado.getRoles() ){
				if ( r.isAdmin() ){
					somenteAdmin = false;
					break;
				}
			}
			
			String sql = null;
			if ( somenteAdmin ){
				sql = this.queryManager.getQuery( SQL_SELECT_ADMINS_BY_MODULO );
			}
			else{
				sql = this.queryManager.getQuery( SQL_SELECT_BY_MODULOS );
			}
			
			//modulos
			StringBuilder sb = new StringBuilder();
			for (  Role r : userLogado.getRoles() ){
				sb.append( r.getModulo().getId() );
				sb.append( "," );
			}
			if ( sb.length() > 1 ){
				sb = sb.deleteCharAt( sb.length() -1 );
			}
			sql = sql.replace("$1", sb.toString() );
			ps = this.connection.prepareStatement( sql );
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				retorno.add( this.createEntity(rs) );
			}
		} catch (Exception e) {
			logController.printLog(e, true);
		} 
		
		return retorno;
	}
	
	public void setUltimoAcesso( List<UsuarioDTO> lista ){
		PreparedStatement ps = null;
		try {
			String query = this.queryManager.getQuery("usuario.dto.getUltimoAcesso");
			StringBuilder sb = new StringBuilder();
			for ( UsuarioDTO u : lista ){
				sb.append( u.getId() );
				sb.append( "," );
			}
			if ( sb.length() > 1 ){
				sb = sb.deleteCharAt( sb.length() -1 );
			}
			query = query.replace("$1", sb.toString() );
			ps = this.connection.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("cd_usuario");
				Date acesso = new java.util.Date( rs.getTimestamp("ultimo_acesso").getTime() );  
				for ( UsuarioDTO u : lista ){
					if ( id == u.getId() ){
						u.setUltimoAcesso( acesso );
						break;
					}
				}
			}
		} catch (Exception e) {
			logController.printLog(e, true);
		} 
	}
	
	public void setUltimaAcao( List<SessaoAtivaDTO> users ){
		PreparedStatement ps = null;
		try {
			String query = this.queryManager.getQuery( "usuario.dto.getUltimaAcao" );
			ps = this.connection.prepareStatement( query );
	        for( SessaoAtivaDTO s: users ){
	        	try{
		        	ps.clearParameters();
		        	ps.setInt(1, s.getIdUser());
		        	ps.setInt(2, s.getIdUser());
		        	ResultSet rs = ps.executeQuery();
		        	if ( rs.next() ){
		        		s.setUltimaAcao( rs.getString("ds_log") );
		        	}
		        	rs.close();
	        	}
	        	catch( Exception e ){
	        		e.printStackTrace();
	        	}
	        }
		} catch (Exception e) {
			logController.printLog(e, true);
		}
	}
	
	private UsuarioDTO createEntity( ResultSet rs ) throws SQLException {
		return new UsuarioDTO( rs.getInt("id_user"), 
							   rs.getString("login"), 
							   rs.getString("nome"),
							   rs.getString("email"),
							   rs.getInt("bloqueado") == 0 ? false : true,
							   rs.getInt("expirado") == 0 ? false : true );
	}
	
	
	/**************************
	 * 
	 * Queries para configurar a utilizacao do novo sistema (flex)
	 * 
	 ***************************/
	public boolean getNovoSistema( int idUser ){
		PreparedStatement ps = null;
		boolean novo = false;
		
		try {
			String query = this.queryManager.getQuery("usuario.getNovoSistema");
			ps = this.connection.prepareStatement( query );
		    ps.setInt(1, idUser);
		    ResultSet rs = ps.executeQuery();
		    if ( rs.next() ){
		    	novo = rs.getInt("fg_novo") == 1 ? true : false;
		    }
		    rs.close();
		} catch (Exception e) {
			logController.printLog(e, true);
		}

		return novo;
	}
	
	public boolean setNovoSistema( int idUser, boolean novo ){
		PreparedStatement ps = null;
		try {
			String query = this.queryManager.getQuery( "usuario.deleteNovoSistema" );
			ps = this.connection.prepareStatement( query );
		    ps.setInt(1, idUser);
		    ps.execute();
		    
		    if ( novo ){
			    ps.getConnection().close();
			    ps.close();
			    
			    // TODO Verificar esta conexão, pois esta sendo fechado acima e aberto novamente.
			    query = this.queryManager.getQuery( "usuario.insertNovoSistema" );
		    	ps = this.connection.prepareStatement( query );
		    	ps.setInt(1, idUser);
			    ps.execute();
		    }
		    return true;
		    
		} catch (Exception e) {
			logController.printLog(e, true);
		} 
		
		return false;
	}
}
