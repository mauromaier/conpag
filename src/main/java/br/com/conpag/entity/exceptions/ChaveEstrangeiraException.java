package br.com.conpag.entity.exceptions;

public class ChaveEstrangeiraException extends Exception {
	
	private static final long serialVersionUID = 2241738170684648145L;

	public ChaveEstrangeiraException( String message ){
		super(message);
	}
	
	public ChaveEstrangeiraException( Exception e ){
		super(e);
	}

}
