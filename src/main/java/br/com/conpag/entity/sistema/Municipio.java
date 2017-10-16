package br.com.conpag.entity.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table( name="sis_municipio" )
@NamedQueries({
	@NamedQuery(name = Municipio.JPQL_SELECT_UFS, query = "SELECT m.uf FROM Municipio m GROUP BY m.uf ORDER BY m.uf"),
	@NamedQuery(name = Municipio.JPQL_SELECT_BY_UF, query = "SELECT m FROM Municipio m WHERE m.uf = :uf ORDER BY m.nome"),
	@NamedQuery(name = Municipio.JPQL_SELECT_BY_NOME, query = "SELECT m FROM Municipio m WHERE m.nome = :nome"),
	@NamedQuery(name = Municipio.JPQL_SELECT_BY_NOME_SC, query = "SELECT m FROM Municipio m WHERE m.nome = :nome AND m.uf = 'SC'"),
	@NamedQuery(name = Municipio.JPQL_SELECT_BY_NOME_AND_UF, query = "SELECT m FROM Municipio m WHERE m.nome = :nome AND m.uf = :uf"),
	@NamedQuery(name = Municipio.JPQL_SELECT_ALL, query = "SELECT m FROM Municipio m ORDER BY m.nome")
    })
public class Municipio implements Serializable, Comparable<Municipio> {
	
	public static final String JPQL_SELECT_ALL = "Municipio.selectAll";
	public static final String JPQL_SELECT_UFS = "Municipio.selectUfs";
	public static final String JPQL_SELECT_BY_UF = "Municipio.selectByUf";
	public static final String JPQL_SELECT_BY_NOME = "Municipio.selectByNome";
	public static final String JPQL_SELECT_BY_NOME_SC = "Municipio.selectByNomeSC";
	public static final String JPQL_SELECT_BY_NOME_AND_UF = "Municipio.selectByNomeAndUf";
	
	private static final long serialVersionUID = 7452098303014505279L;

	@Id
    @Column(name="id_municipio")
	private int id;
	
	@Column(name="nome", nullable=false )
	private String nome;
	
	@Column(name="uf" )
	private String uf;
	
	@Column(name="nu_latitude" )
	private Double latitude;
	
	@Column(name="nu_longitude" )
	private Double longitude;

	public Municipio() {
		super();
	}
	
	public Municipio(int id, String nome, String uf) {
		super();
		this.id = id;
		this.nome = nome;
		this.uf = uf;
	}

	@Override
	public String toString(){
		return this.nome;
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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public int compareTo(Municipio o) {
		return this.nome.compareTo( o.getNome() );
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Municipio m = (Municipio)obj;
		if ( m.getId() == this.getId() )
			return true;
		return false;
	}
	

}
