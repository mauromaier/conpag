package br.com.conpag.entity.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table( name="sis_modulos" )
@NamedQueries({
    @NamedQuery(name = Modulo.JPQL_SELECT_ALL, query = "SELECT m FROM Modulo m ORDER BY m.nome" )
    })
public class Modulo implements Serializable {
	
	public static final String JPQL_SELECT_ALL = "Modulo.selectAll";
	
	private static final long serialVersionUID = 3872148646780250915L;

	@Id
    @GeneratedValue( strategy=GenerationType.IDENTITY )
	@Column(name="id_modulo" )
	private int id;
	
	@Column(name="nome" )
	private String nome;
	
	@Column(name="desc" )
	private String descricao;
	
	public Modulo() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean equals( Object o ){
		if ( !(o instanceof Modulo) )
			return false;
		Modulo m = (Modulo)o;
		if ( m.getId() != this.getId() )
			return false;
		return true;
	}
}
