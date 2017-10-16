package br.com.conpag.entity.exceptions;

public class CRUDException extends Exception {
	
	
	private static final long serialVersionUID = -1695573075173071075L;

	public CRUDException( String message ){
		super(message);
	}
	
	public CRUDException( Exception e ){
		super(e);
	}

}
