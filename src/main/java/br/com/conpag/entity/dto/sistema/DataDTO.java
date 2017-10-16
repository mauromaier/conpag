package br.com.conpag.entity.dto.sistema;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataDTO extends java.util.Date {

	private static final long serialVersionUID = -5214496591457218980L;

	public DataDTO(){
		
	}
	
	public DataDTO( long date ){
		super(date);
	}
	
	public DataDTO( int mes, int ano ){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, mes - 1);
		c.set(Calendar.YEAR, ano );
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR, 1);
		this.setTime( c.getTimeInMillis() );
	}
	
	public String toString(){
		return new SimpleDateFormat("MM/yyyy").format(this);
	}
}
