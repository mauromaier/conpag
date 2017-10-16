package br.com.conpag.entity.sistema;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TipoAtividadeGeneric {
	
	@Id
	@Column(name="cd_id")
	private int id;
	
	@Column(name="ds_descricao")
	private String descricao;
	
	@Column(name="ds_sigla")
	private String sigla;

	@Column( name="fg_ativo")
	private boolean ativo;
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	
}
