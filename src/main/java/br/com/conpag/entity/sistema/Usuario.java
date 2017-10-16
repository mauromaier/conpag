package br.com.conpag.entity.sistema;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name="sis_user" )
@NamedQueries({
    @NamedQuery(name = Usuario.JPQL_DO_LOGIN, query = "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha" ),
    @NamedQuery(name = Usuario.JPQL_FIND_BY_LOGIN, query = "SELECT u FROM Usuario u WHERE u.login = :login" ),
    @NamedQuery(name = Usuario.JPQL_SELECT_ALL, query = "SELECT u FROM Usuario u ORDER BY u.nome" ),
    @NamedQuery(name = Usuario.JPQL_UPDATE_SENHA, query = "UPDATE Usuario u SET u.senha = :senha WHERE u.id = :id" )
    })
public class Usuario implements Serializable, Principal {
	
	public static final String JPQL_DO_LOGIN = "Usuario.doLogin";
	public static final String JPQL_FIND_BY_LOGIN = "Usuario.findByLogin";
	public static final String JPQL_FIND_BY_GERENCIA = "Usuario.findByGerencia";
	public static final String JPQL_SELECT_ALL = "Usuario.selectAll";
	public static final String JPQL_UPDATE_SENHA = "Usuario.updateSenhaNoEncrypt";
	
	private static final long serialVersionUID = 5669321552872153747L;

	@Id
    @GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name="id_user")
	private int id;
	
	@Column(name="login", nullable=false, unique=true )
	private String login;
	
	@Column(name="password", nullable=false )
	private String senha;
	
	@Column(name="nome", nullable=false )
	private String nome;
	
	@Column(name="email", nullable=false )
	private String email;
	
	@Column(name="bloqueado" )
	private boolean bloqueado;
	
	@Column(name="expirado" )
	private boolean expirado;
		
	@JoinColumn(name = "id_municipio", referencedColumnName = "id_municipio")
    @ManyToOne
	private Municipio municipio;
	
	@Column(name="nu_tentativas" )
	private int numTentativas;
	
	@Transient
	private int msgNaoLidas;
	
	@ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)  
	@JoinTable(name="sis_user_perfil", joinColumns={@JoinColumn(name="id_user")}, inverseJoinColumns={@JoinColumn(name="id_perfil")}) 
	private List<Role> roles;

	public Usuario() {
		super();
	}
	
	public Usuario(int id, String login, String senha, String nome,
			String email, boolean bloqueado, boolean expirado) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.bloqueado = bloqueado;
		this.expirado = expirado;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", nome=" + nome + ", email=" + email
				+ ", bloqueado=" + bloqueado + ", expirado=" + expirado + ", municipio="
				+ municipio + ", numTentativas=" + numTentativas + ", msgNaoLidas=" + msgNaoLidas + ", roles=" + roles
				+ "]";
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public int getNumTentativas() {
		return numTentativas;
	}

	public void setNumTentativas(int numTentativas) {
		this.numTentativas = numTentativas;
	}

	public int getMsgNaoLidas() {
		return msgNaoLidas;
	}

	public void setMsgNaoLidas(int msgNaoLidas) {
		this.msgNaoLidas = msgNaoLidas;
	}
	public Municipio getMunicipio() {
		return municipio;
	}
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	@JsonIgnore
	@Override
	public String getName() {
		return this.login;
	}
	
}
