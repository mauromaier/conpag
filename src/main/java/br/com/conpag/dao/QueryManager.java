package br.com.conpag.dao;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

@Singleton
public class QueryManager {

	private Properties queries;
	
	
	private static final String ARQUIVO = "/properties/queries.properties"; 
	 

	@PostConstruct
	public void init(){
		this.queries = new Properties();
		
		try{
			queries.load( this.getClass().getResourceAsStream(ARQUIVO) );
		}
		catch (Exception e) {
			System.out.println( "Impossivel carregar a lista de queries: " + e.getMessage() );
		}
		
	}
	
	public String getQuery( String key ){
		return this.queries.getProperty(key);
	}
	
}
