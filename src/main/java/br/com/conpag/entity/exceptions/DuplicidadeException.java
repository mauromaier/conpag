package br.com.conpag.entity.exceptions;

public class DuplicidadeException extends Exception{

	private static final long serialVersionUID = 8235537679322361174L;

	public DuplicidadeException( String message ){
		super(message);
	}
	
	public boolean getDuplicidade(){
		return true;
	}
}
