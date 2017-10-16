package br.com.conpag.entity.dto.sistema;

import java.util.Date;

public class LogDTO {

	private int idLog;
	private String modulo;
	private String usuario;
	private Date data;
	private String textoLog;
	private int tipo;
	
	public LogDTO(int idLog, String modulo, String usuario, Date data, String textoLog, int tipo) {
		super();
		this.idLog = idLog;
		this.modulo = modulo == null ? "---" : modulo;
		this.usuario = usuario == null ? "---" : usuario;
		this.data = data;
		this.textoLog = textoLog;
		this.tipo = tipo;
	}

	public int getIdLog() {
		return idLog;
	}
	public void setIdLog(int idLog) {
		this.idLog = idLog;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTextoLog() {
		return textoLog;
	}
	public void setTextoLog(String textoLog) {
		this.textoLog = textoLog;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
}
