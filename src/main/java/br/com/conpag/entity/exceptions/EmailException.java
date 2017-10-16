package br.com.conpag.entity.exceptions;

public class EmailException extends Exception{


	private static final long serialVersionUID = -409033683516840504L;

	public EmailException( String message ){
		super(message);
	}
	
	public boolean getErroEnvio(){
		return true;
	}
	
}
