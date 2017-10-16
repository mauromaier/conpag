package br.com.conpag.entity.dto.sistema;

import java.util.Date;

import javax.servlet.http.HttpSession;

import br.com.conpag.entity.sistema.Usuario;

public class SessaoAtivaDTO {

	private int idUser;
	private String jsessionid;
	private String login;
	private int tempoAtivo;
	private int tempoOcioso;
	private String local;
	private String ultimaAcao;
	
	public SessaoAtivaDTO(){
	}
	
	public SessaoAtivaDTO( HttpSession session ){
		
		if( session.getAttribute("user") != null ){
			Usuario user = (Usuario)session.getAttribute("user");
			login = user.getLogin();
			this.idUser = user.getId();
		}
		
		long agora = new Date().getTime();
		long criacao = agora - session.getCreationTime();
		long ocioso = agora - session.getLastAccessedTime();
		
		//divipe por milisegundos e segundos (para chegar no minuto)
		criacao /= (60*1000);
		ocioso /= (60*1000);
		
		this.tempoAtivo = (int)criacao;
		this.tempoOcioso = (int)ocioso;
		
		this.jsessionid = session.getId();
		
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getTempoAtivo() {
		return tempoAtivo;
	}
	public void setTempoAtivo(int tempoAtivo) {
		this.tempoAtivo = tempoAtivo;
	}
	public int getTempoOcioso() {
		return tempoOcioso;
	}
	public void setTempoOcioso(int tempoOcioso) {
		this.tempoOcioso = tempoOcioso;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getJsessionid() {
		return jsessionid;
	}
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}
	public String getUltimaAcao() {
		return ultimaAcao;
	}
	public void setUltimaAcao(String ultimaAcao) {
		this.ultimaAcao = ultimaAcao;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
}
