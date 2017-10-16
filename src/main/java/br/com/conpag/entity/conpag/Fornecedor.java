package br.com.conpag.entity.conpag;

import java.util.Calendar;

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

import br.com.conpag.entity.sistema.Usuario;

@Entity
@Table(name = "mod_conpag_fornecedores")
@NamedQueries({
	@NamedQuery(name = Fornecedor.JPQL_GET_FORNECEDOR, query = "SELECT  f FROM Fornecedor as f WHERE f.fgExcluido = false"),
	@NamedQuery(name = Fornecedor.JPQL_GET_FORNECEDOR_BY_PARAMS, query = "SELECT  f FROM Fornecedor as f WHERE f.cnpjCpf = :cnpjCpf AND f.fgExcluido = false ") })

public class Fornecedor {


	public static final String JPQL_GET_FORNECEDOR = "Fornecedor.getFornecedor()";
	public static final String JPQL_GET_FORNECEDOR_BY_PARAMS = "Fornecedor.getFornecedorByParams()";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cd_id")
	private int id;
	
	@Column(name = "ds_razao_social_nome")
	private String razaoNome;
	
	@Column(name = "ds_cnpj_cpf")
	private String cnpjCpf;
	
	@Column(name = "ds_nome_fantasia")
	private String nomeFantasia;
	
	@Column(name = "ds_insc_estadual_rg")
	private String inscEstadualRg;
	
	@JoinColumn(name = "cd_endereco", referencedColumnName = "cd_id")
	@ManyToOne
	private Enderecos endereco;
	
	@Column(name = "ds_numero_rua")
	private String nuRua;

	@Column(name = "ds_fone_01")
	private String telefone;

	@Column(name = "ds_fone_02")
	private String telefone02;
	
	@Column(name = "ds_email")
	private String email;
	
	@JoinColumn(name = "cd_banco", referencedColumnName = "cd_id")
	@ManyToOne
	private Bancos banco;
	
	@Column(name = "ds_agencia")
	private String agencia;
	
	@Column(name = "ds_conta")
	private String conta;
	
	@Column(name = "ds_complemento")
	private String complemento;
	
	@JoinColumn(name = "cd_usuario", referencedColumnName = "id_user")
	@ManyToOne
	private Usuario usuario;

	@Column(name = "ds_obs")
	private String observacao;
	
	@Column(name = "fg_excluido")
	private boolean fgExcluido;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao")
	private Calendar dtCriacao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRazaoNome() {
		return razaoNome;
	}

	public void setRazaoNome(String razaoNome) {
		this.razaoNome = razaoNome;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getInscEstadualRg() {
		return inscEstadualRg;
	}

	public void setInscEstadualRg(String inscEstadualRg) {
		this.inscEstadualRg = inscEstadualRg;
	}

	public Enderecos getEndereco() {
		return endereco;
	}

	public void setEndereco(Enderecos endereco) {
		this.endereco = endereco;
	}

	public String getNuRua() {
		return nuRua;
	}

	public void setNuRua(String nuRua) {
		this.nuRua = nuRua;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone02() {
		return telefone02;
	}

	public void setTelefone02(String telefone02) {
		this.telefone02 = telefone02;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Bancos getBanco() {
		return banco;
	}

	public void setBanco(Bancos banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isFgExcluido() {
		return fgExcluido;
	}

	public void setFgExcluido(boolean fgExcluido) {
		this.fgExcluido = fgExcluido;
	}

	public Calendar getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Calendar dtCriacao) {
		this.dtCriacao = dtCriacao;
	}
	
	
}
