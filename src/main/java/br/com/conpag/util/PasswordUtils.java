package br.com.conpag.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Singleton;

@Singleton
public class PasswordUtils {

	private static final char[] hexChars = {
        '0','1','2','3','4','5','6','7',
        '8','9','a','b','c','d','e','f'};
	
	//apenas frutas com mais de 6 letras e sem acentos
	private static final String [] prefixos = {
		"abacaxi",		"acerola",
		"abacate",		"amora",
		"castanha",		"cereja",
		"ameixa",		"carambola",
		"goiaba",		"laranja",
		"morango",		"melancia",
		"pitanga",		"tangerina",
		"framboesa"		
	};
	
	public String encript(String s){
		try{
			 MessageDigest md = MessageDigest.getInstance("SHA-1");
	            md.update(s.getBytes(), 0, s.getBytes().length);
	            byte[] hash = md.digest();
	            StringBuffer sb = new StringBuffer();
	            int msb;
	            int lsb = 0;
	            int i;
	            for (i = 0; i < hash.length; i++) {
	                msb = ((int)hash[i] & 0x000000FF) / 16;
	                lsb = ((int)hash[i] & 0x000000FF) % 16;
	                sb.append(hexChars[msb]);
	                sb.append(hexChars[lsb]);
	            }
	            return sb.toString();

		}
		catch(NoSuchAlgorithmException nsae){
			System.out.println("Excecao: "+ nsae);
			return null;
		}
	}
	
	
	public String geraSenha(int tamanho){
		String chars = new  String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!?,.");
	 	StringBuffer sb = new StringBuffer();
	 	for (int i = 0; i< tamanho;i++){
	 		sb.append( chars.charAt( (int)(Math.random()*chars.length() ) ) );
	 	}
	 	return sb.toString();
	}
	
	public String geraSenhaTop(){
		StringBuffer sb = new StringBuffer();
		sb.append( prefixos[ (int)(Math.random()*prefixos.length ) ] );
		int num = (int)(Math.random()* 20 );
		if ( num < 10 )
			sb.append( 0 );
		sb.append( num );
		return sb.toString();
	}
}
