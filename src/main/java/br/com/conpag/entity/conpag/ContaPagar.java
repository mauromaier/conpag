package br.com.conpag.entity.conpag;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.conpag.entity.sistema.Usuario;

@Entity
@Table(name="mod_conpag_contas_pagar")
public class ContaPagar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cd_id")
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_emissao")
	private Calendar dtEmissao;
	
	@JoinColumn(name = "cd_fornecedor", referencedColumnName = "cd_id")
	@ManyToOne
	private Fornecedor fornecedor; 
	
	@JoinColumn(name = "cd_empresa", referencedColumnName = "cd_id")
	@ManyToOne
	private Empresa empresa;
	
	@JoinColumn(name = "cd_usuario", referencedColumnName = "id_user")
	@ManyToOne
	private Usuario usuario;
	
	@Column(name = "fg_excluido")
	private boolean fgExcluido;
	
	private String descricao;
	
	@Column(name="ds_portador")
	private String portador;
	
	@Column(name="ds_documento")
	private String documento;

	@JoinColumn(name = "cd_grupo", referencedColumnName = "cd_id")
	@ManyToOne
	private Grupo grupo;
	
	@OneToMany(mappedBy = "conta", orphanRemoval = true, targetEntity = Parcelas.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Parcelas> listaParcelas;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPortador() {
		return portador;
	}

	public void setPortador(String portador) {
		this.portador = portador;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}


	public List<Parcelas> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<Parcelas> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Calendar getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Calendar dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public boolean isFgExcluido() {
		return fgExcluido;
	}

	public void setFgExcluido(boolean fgExcluido) {
		this.fgExcluido = fgExcluido;
	}
	
}
