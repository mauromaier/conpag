package br.com.conpag.entity.conpag;

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

import br.com.conpag.entity.sistema.Municipio;

@Entity
@Table(name = "mod_conpag_bairros")
@NamedQueries({
		@NamedQuery(name = Bairros.JPQL_GET_BAIRROS, query = "SELECT  b FROM Bairros as b ORDER BY b.nome ASC"),
		@NamedQuery(name = Bairros.JPQL_GET_BAIRROS_BY_MUNICIPIO, query = "SELECT b FROM Bairros b WHERE b.municipio = :municipio ORDER BY b.nome ASC") })
public class Bairros {

	public static final String JPQL_GET_BAIRROS = "Bairros.getBairros()";
	public static final String JPQL_GET_BAIRROS_BY_MUNICIPIO = "Bairros.getBairrosByMunicipio()";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_id")
	private int id;

	@Column(name = "nome")
	private String nome;

	@JoinColumn(name = "cd_municipio")
	@ManyToOne
	private Municipio municipio;

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
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

}
