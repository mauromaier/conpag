package br.com.conpag.entity.dto.sistema;

import java.util.Date;

public class UsuarioDTO {
	
	private int id;
	private String login;
	private String nome;
	private String email;
	private boolean bloqueado;
	private boolean expirado;
	private Date ultimoAcesso;
	
	public UsuarioDTO() {
		super();
	}
	
	public UsuarioDTO(int id, String login, String nome, String email,
			boolean bloqueado, boolean expirado) {
		super();
		this.id = id;
		this.login = login;
		this.nome = nome;
		this.email = email;
		this.bloqueado = bloqueado;
		this.expirado = expirado;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	public boolean isExpirado() {
		return expirado;
	}
	public void setExpirado(boolean expirado) {
		this.expirado = expirado;
	}
	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}
	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}
}
