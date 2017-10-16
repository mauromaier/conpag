package br.com.conpag.entity.exceptions;

public class NoUserException extends Exception {
	
	
	private static final long serialVersionUID = -1625861194718519465L;

	
	
	public NoUserException(){
		super("Usuario não encontrado");
	}
	

	public NoUserException( String message ){
		super(message);
	}
	
	public NoUserException( Exception e ){
		super(e);
	}
	
	public boolean getNoUser(){
		return true;
	}

}
