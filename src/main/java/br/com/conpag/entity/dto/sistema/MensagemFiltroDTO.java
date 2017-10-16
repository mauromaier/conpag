package br.com.conpag.entity.dto.sistema;

import java.util.Date;

public class MensagemFiltroDTO {

	private int idUser;
	private Date inicio;
	private Date fim;
	private int idRegional;
	
	private int limite;
	private int pagina;
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	public int getIdRegional() {
		return idRegional;
	}
	public void setIdRegional(int idRegional) {
		this.idRegional = idRegional;
	}
	public int getLimite() {
		return limite;
	}
	public void setLimite(int limite) {
		this.limite = limite;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	@Override
	public String toString() {
		return "MensagemFiltroDTO [idUser=" + idUser + ", inicio=" + inicio + ", fim=" + fim + ", idRegional="
				+ idRegional + "]";
	}
	
}
