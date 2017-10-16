package br.com.conpag.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

public abstract class BaseDAO {

	@Inject
	protected QueryManager queryManager;
	
	@Inject
	protected Connection connection;
	
	
	protected int getOffset( int pagina, int registrosPorPagina){
		return ( pagina -1 ) * registrosPorPagina;
	}
	
	protected int getRowCount( PreparedStatement ps ){
		int registros = 0;
        try {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                registros = rs.getInt("nu_registros");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return registros;
	}
	
	//-Métodos para retornar o proximo id de uma sequence qualquer------------------------
	protected int getNextId( String sequenceKey ){
		PreparedStatement ps = null;
		try {
			String query = this.queryManager.getQuery( sequenceKey );
			ps = this.connection.prepareStatement( query );
			
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ){
			return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
}
