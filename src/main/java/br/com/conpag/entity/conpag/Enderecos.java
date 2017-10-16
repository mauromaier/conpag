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

@Entity
@Table(name = "mod_conpag_enderecos")
@NamedQueries({
		@NamedQuery(name = Enderecos.JPQL_GET_ENDERECOS, query = "SELECT e FROM Enderecos as e ORDER BY e.nome ASC"),
		@NamedQuery(name = Enderecos.JPQL_GET_ENDERECOS_BY_BAIRRO, query = "SELECT e FROM Enderecos e WHERE e.bairro = :bairro ORDER BY e.nome ASC") })
public class Enderecos {

	public static final String JPQL_GET_ENDERECOS = "Enderecos.getEnderecos()";
	public static final String JPQL_GET_ENDERECOS_BY_BAIRRO = "Enderecos.getEnderecosByBairro()";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_id")
	private int id;

	@Column(name = "nome")
	private String nome;

	@JoinColumn(name = "cd_bairro")
	@ManyToOne
	private Bairros bairro;

	@Column(name = "cep")
	private int cep;

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
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

	public Bairros getBairro() {
		return bairro;
	}

	public void setBairro(Bairros bairro) {
		this.bairro = bairro;
	}

}
