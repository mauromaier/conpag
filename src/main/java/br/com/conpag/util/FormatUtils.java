package br.com.conpag.util;

import javax.inject.Singleton;

@Singleton
public class FormatUtils {

	private FormatUtils(){
		
	}
	
	public String formatFone(String fone) {
		if ( fone == null || fone.length() < 8 )
			return fone;
		
		if ( fone.length() == 10 ) {
			return "("+fone.substring(0,2)+") "+fone.substring(2,6)+"-"+fone.substring(6);
		} 
		if ( fone.length() == 8 ) {
			return fone.substring(0,4)+"-"+fone.substring(4);
		}
		return fone;
	}
	
	public String formatCpf(String cpf) {
		if ( cpf == null || cpf.length() < 11 )
			return cpf;
		return cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6,9) + "-" + cpf.substring(9);
	}

}
