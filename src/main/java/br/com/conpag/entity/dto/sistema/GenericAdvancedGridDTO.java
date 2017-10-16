package br.com.conpag.entity.dto.sistema;

import java.util.List;

public class GenericAdvancedGridDTO {
	
	private String nome;
	private List<Object> children;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Object> getChildren() {
		return children;
	}
	public void setChildren(List<Object> children) {
		this.children = children;
	}
	
	

}
