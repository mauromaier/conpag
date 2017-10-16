package br.com.conpag.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ConnectionFactory {

	@Produces
	@RequestScoped
	public Connection getConnection() throws SQLException {
		Connection conn = null;
        
		try {
              Context initialContext = new InitialContext();
              DataSource dataSource = (DataSource)initialContext.lookup("java:jboss/datasources/ConPagDS");
              conn = dataSource.getConnection();
        } catch (Exception e) {
              e.printStackTrace();
        }
		
        return conn;
	}
	
	public void closeConnection( @Disposes Connection con ){
		try {
			if ( con != null ){
				if ( !con.isClosed() ){
					con.close();
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
