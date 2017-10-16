package br.com.conpag.entity.sistema;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table( name="sis_mensagem" )
@NamedQueries({
    @NamedQuery(name = Mensagem.JPQL_SELECT_PENDENTES_BY_DESTINO, query = "SELECT m FROM Mensagem m WHERE m.idUserDestino = :id_user_dest AND m.lida = 0" ),
    @NamedQuery(name = Mensagem.JPQL_SELECT_BY_DATA, query = "SELECT m FROM Mensagem m WHERE m.idUserDestino = :id_user_dest AND m.data BETWEEN :inicio AND :fim ORDER BY m.data DESC" ),
    @NamedQuery(name = Mensagem.JPQL_SELECT_ENVIADAS_BY_DATA, query = "SELECT m FROM Mensagem m WHERE m.remetente.id = :id_user_rem AND m.data BETWEEN :inicio AND :fim ORDER BY m.data DESC" ),
    @NamedQuery(name = Mensagem.JPQL_UPDATE_LIDAS, query = "UPDATE Mensagem SET lida = 1 WHERE id = :id " )
    })
public class Mensagem {
	
	public static final String JPQL_SELECT_PENDENTES_BY_DESTINO = "Mensagem.getNaoLidas";
	public static final String JPQL_SELECT_BY_DATA = "Mensagem.getByData";
	public static final String JPQL_SELECT_ENVIADAS_BY_DATA = "Mensagem.getEnviadasByData";
	public static final String JPQL_UPDATE_LIDAS = "Mensagem.updateLida";
	
	@Id
    @GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name="id")
	private int id;
	
	@JoinColumn(name = "id_user_remetente", referencedColumnName = "id_user")
    @ManyToOne
	private Usuario remetente;
	
	@Column(name="id_user_destino")
	private int idUserDestino;
	
	@Column(name="data" )
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@Column(name="assunto" )
	private String assunto;
	
	@Column(name="texto", columnDefinition="text" )
	private String texto;
	
	@Column(name="msg_lida" )
	private boolean lida;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getRemetente() {
		return remetente;
	}

	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}

	public int getIdUserDestino() {
		return idUserDestino;
	}

	public void setIdUserDestino(int idUserDestino) {
		this.idUserDestino = idUserDestino;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isLida() {
		return lida;
	}

	public void setLida(boolean lida) {
		this.lida = lida;
	}

}
