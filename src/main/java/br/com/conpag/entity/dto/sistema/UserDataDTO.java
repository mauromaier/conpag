package br.com.conpag.entity.dto.sistema;

import java.util.Date;

public class UserDataDTO {
	
	public String nome;
	public Date data;
	
	public UserDataDTO() {
		super();
	}
	public UserDataDTO(String nome, Date data) {
		super();
		this.nome = nome;
		this.data = data;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	

}
