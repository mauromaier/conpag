package br.com.conpag.entity.sistema;

public class ParametroSistema {
	
	private int id;
	private String chave;
	private String valor;
	
	public ParametroSistema() {
		super();
	}
	public ParametroSistema(int id, String chave, String valor) {
		super();
		this.id = id;
		this.chave = chave;
		this.valor = valor;
	}

	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
