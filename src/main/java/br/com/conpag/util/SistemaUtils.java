package br.com.conpag.util;

import java.io.File;

import javax.inject.Singleton;

/**
 * 
 * Esta classe deve ser utilizada para buscar informacoes pertinentes ao sistema em geral
 * 
 * @author hallanmedeiros
 *
 */
@Singleton
public class SistemaUtils {
	
	private SistemaUtils(){
	}
	
	// TODO alterar PATH pois será alterado posteriormente.
	public String getAppPath(){
		return System.getProperty("catalina.home") + File.separator +
		  "webapps" + File.separator + "vigilantos3";
	}

}
