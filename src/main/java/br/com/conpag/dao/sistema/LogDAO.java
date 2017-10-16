package br.com.conpag.dao.sistema;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import br.com.conpag.controller.sistema.LogController;
import br.com.conpag.controller.sistema.ModuloController;
import br.com.conpag.dao.BaseDAO;
import br.com.conpag.entity.dto.sistema.LogDTO;
import br.com.conpag.entity.sistema.Modulo;
import br.com.conpag.util.Log;

public class LogDAO extends BaseDAO {
	
	@EJB
	private ModuloController moduloController;

	@Inject
	private LogController logController;
	
	private static String SQL_INSERT_LOG = "log.insert";
	
	private List<Modulo> modulos;
	
	@PostConstruct
	public void init() {
		try {
			this.modulos = moduloController.getAll();
		} catch (Exception e) {
			logController.printLog("[LogDAO] Impossivel buscar os modulos do sistema: ", e, Log.ERRO, true);
		}
	}
	
	public int getIdModulo( String nome ){
		for ( Modulo m: this.modulos ){
			if ( m.getNome().equalsIgnoreCase( nome ) )
				return m.getId();
		}
		return -1;
	}
	
	
	public void saveLog( String nomeModulo, int user, String strLog, int tipo ){
		PreparedStatement ps = null;
		try{
			int x = 1;
			int modulo = this.getIdModulo( nomeModulo );
			String sql = this.queryManager.getQuery( SQL_INSERT_LOG );
			ps = connection.prepareStatement( sql );
			
			if ( modulo > 0 )
				ps.setInt( x++ , modulo );
			else
				ps.setNull(x++, Types.INTEGER );
			
			
			if ( user > 0 )
				ps.setInt( x++ , user );
			else
				ps.setNull(x++, Types.INTEGER );
			
			ps.setString( x++ , strLog );
			ps.setInt( x++ , tipo );
			ps.setBoolean(x++, false);
			
			ps.execute();
		}
		catch (Exception e){
			logController.printLog(e, true);
		}
	}
	
	public List<LogDTO> selectAll( Date inicio, Date fim ){
		List<LogDTO> list = new ArrayList<LogDTO>();
		PreparedStatement ps = null;
		try{
			int x = 1;
			String sql = this.queryManager.getQuery( "log.selectAll" );
			ps = connection.prepareStatement( sql );
				
			ps.setTimestamp( x++ , new java.sql.Timestamp( inicio.getTime() ) );
			ps.setTimestamp( x++ , new java.sql.Timestamp( fim.getTime() ) );
			
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ){
				
				list.add( this.createEntityDTO( rs ) );
			}
		}
		catch (SQLException ex){
			logController.printLog(ex, true);
		}
		return list;
	}
	
	public List<LogDTO> selectByUser( int idUser, Date inicio, Date fim ){
		List<LogDTO> list = new ArrayList<LogDTO>();
		PreparedStatement ps = null;
		try{
			int x = 1;
			if ( idUser < 1 ){
				String sql = this.queryManager.getQuery( "log.selectByUserNull" );
				ps = this.connection.prepareStatement( sql );
			}
			else{
				String sql = this.queryManager.getQuery( "log.selectByUser" );
				ps = this.connection.prepareStatement( sql );
				ps.setInt( x++, idUser );
			}
			
			ps.setTimestamp( x++ , new java.sql.Timestamp( inicio.getTime() ) );
			ps.setTimestamp( x++ , new java.sql.Timestamp( fim.getTime() ) );
			
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ){
				
				list.add( this.createEntityDTO( rs ) );
			}
		}
		catch (SQLException ex){
			logController.printLog(ex, true);
		}
		return list;
	}
	
	
	public List<LogDTO> selectByModulo( Modulo modulo, Date inicio, Date fim ){
		List<LogDTO> list = new ArrayList<LogDTO>();
		PreparedStatement ps = null;
		try{
			int x = 1;
			if ( modulo == null ){
				String sql = this.queryManager.getQuery("log.selectByModuloNull");
				ps = this.connection.prepareStatement( sql );
			}
			else{
				String sql = this.queryManager.getQuery("log.selectByModulo");
				ps = this.connection.prepareStatement( sql );
				ps.setInt( x++, modulo.getId() );
			}
			
			ps.setTimestamp( x++ , new java.sql.Timestamp( inicio.getTime() ) );
			ps.setTimestamp( x++ , new java.sql.Timestamp( fim.getTime() ) );
			
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ){
				
				list.add( this.createEntityDTO( rs ) );
			}
		}
		catch (SQLException ex){
			logController.printLog(ex, true);
		}
		return list;
	}
	
	public List<LogDTO> selectByUserModulo( int idUser, Modulo modulo, Date inicio, Date fim ){
		List<LogDTO> list = new ArrayList<LogDTO>();
		PreparedStatement ps = null;
		try{
			int x = 1;
			if ( modulo == null && idUser < 1 ){
				String sql = this.queryManager.getQuery("log.selectByUserNullModuloNull");
				ps = this.connection.prepareStatement( sql );
			}
			else if ( modulo == null ){
				String sql = this.queryManager.getQuery("log.selectByUserModuloNull");
				ps = this.connection.prepareStatement( sql );
				ps.setInt( x++, idUser );
			}
			else if ( idUser < 1 ){
				String sql = this.queryManager.getQuery("log.selectByUserNullModulo");
				ps = this.connection.prepareStatement( sql );
				ps.setInt( x++, modulo.getId() );
			}
			else{
				String sql = this.queryManager.getQuery("log.selectByUserModulo");
				ps = this.connection.prepareStatement( sql );
				ps.setInt( x++, idUser );
				ps.setInt( x++, modulo.getId() );
			}
			
			ps.setTimestamp( x++ , new java.sql.Timestamp( inicio.getTime() ) );
			ps.setTimestamp( x++ , new java.sql.Timestamp( fim.getTime() ) );
			
			ResultSet rs = ps.executeQuery();
			while ( rs.next() ){
				
				list.add( this.createEntityDTO( rs ) );
			}
		}
		catch (SQLException ex){
			logController.printLog(ex, true);
		}
		return list;
	}
	

	
	private LogDTO createEntityDTO( ResultSet rs ) throws SQLException{
		Date data = new Date( rs.getTimestamp("dt_data").getTime() );
		LogDTO dto = new LogDTO(  rs.getInt("cd_id"), 
								rs.getString("nm_modulo"), 
								rs.getString("login"), 
								data, 
								rs.getString("ds_log"), 
								rs.getInt("nu_tipo")
								);
		return dto;
	}
	
	
	
	

}
