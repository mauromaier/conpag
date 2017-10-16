package br.com.conpag.entity.conpag;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mod_conpag_bancos")
@NamedQueries({ @NamedQuery(name = Bancos.JPQL_GET_BANCOS, query = "SELECT  b FROM Bancos as b ORDER BY b.nome ASC") })
public class Bancos {

	public static final String JPQL_GET_BANCOS = "Bancos.getBancos()";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_id")
	private int id;

	@Column(name = "nome")
	private String nome;

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
