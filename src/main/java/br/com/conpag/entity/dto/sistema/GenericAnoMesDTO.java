package br.com.conpag.entity.dto.sistema;

public class GenericAnoMesDTO {
	
	private int ano;
	private int mes;
	private String mesExtenso;
	private long total;
	
	public GenericAnoMesDTO() {
		super();
	}
	
	public GenericAnoMesDTO(int ano, int mes, String mesExtenso, long total) {
		super();
		this.ano = ano;
		this.mes = mes;
		this.mesExtenso = mesExtenso;
		this.total = total;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getMesExtenso() {
		return mesExtenso;
	}
	public void setMesExtenso(String mesExtenso) {
		this.mesExtenso = mesExtenso;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	
	

}
