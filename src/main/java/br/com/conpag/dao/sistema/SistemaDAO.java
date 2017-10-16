package br.com.conpag.dao.sistema;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.conpag.dao.BaseDAO;

public class SistemaDAO extends BaseDAO {
	
	private static String SQL_SET_PARAMETER = "sistema.setParameter";
	private static String SQL_GET_PARAMETER = "sistema.getParameter";
	
	public String getParameter( String key ){
		String query = this.queryManager.getQuery( SQL_GET_PARAMETER );
		
        try {
        	PreparedStatement ps = this.connection.prepareStatement( query );
    		
        	ps.setString(1, key );
			ResultSet rs = ps.executeQuery();
			if ( rs.next() ){
				String value = rs.getString("ds_valor");
				return value;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean setParameter( String key, String value ){
		String query = this.queryManager.getQuery( SQL_SET_PARAMETER );
		
        try {
        	PreparedStatement ps = this.connection.prepareStatement( query );
    		ps.setString(2, key );
        	ps.setString(1, value );
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return false;
	}
	
}
