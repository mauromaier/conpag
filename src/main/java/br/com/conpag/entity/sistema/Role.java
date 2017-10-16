package br.com.conpag.entity.sistema;


import java.io.Serializable;
import java.security.Principal;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name="sis_perfil" )
@NamedQueries({
	@NamedQuery(name = Role.JPQL_SELECT_ALL, query = "SELECT r FROM Role r"),
	@NamedQuery(name = Role.JPQL_SELECT_BY_MODULO, query = "SELECT r FROM Role r WHERE r.modulo = :modulo")
    })
public class Role implements Serializable, Principal {
	
	public static final String JPQL_SELECT_ALL = "Role.selectAll";
	public static final String JPQL_SELECT_BY_MODULO = "Role.selectByModulo";
	
	private static final long serialVersionUID = -8679653853856653763L;

	@Id
    @GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name="id_perfil")
	private int id;
	
	@JoinColumn(name = "id_modulo", referencedColumnName = "id_modulo")
    @ManyToOne
	private Modulo modulo;
	
	@Column(name="desc", nullable=false )
	private String descricao;
	
	@Column(name="fg_admin", nullable=false )
	private boolean admin;
	

	public Role() {
		super();
	}

	public Role(int id, Modulo modulo, String descricao) {
		super();
		this.id = id;
		this.modulo = modulo;
		this.descricao = descricao;
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
	public Modulo getModulo() {
		return modulo;
	}
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String toString(){
		return this.descricao;
	}

	@JsonIgnore
	@Override
	public String getName() {
		return this.getModulo().getNome() + "_" + this.descricao;
	}

}
