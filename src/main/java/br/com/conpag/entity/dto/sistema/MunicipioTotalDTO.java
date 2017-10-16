package br.com.conpag.entity.dto.sistema;

public class MunicipioTotalDTO {
	
	public String nome;
	public int total;
	
	public MunicipioTotalDTO(String nome, int total) {
		super();
		this.nome = nome;
		this.total = total;
	}
	
	public String getNome() {
		return nome;
	}
	public int getTotal() {
		return total;
	}
	
}
