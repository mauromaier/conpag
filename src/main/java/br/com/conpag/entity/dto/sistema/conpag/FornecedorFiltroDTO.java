package br.com.conpag.entity.dto.sistema.conpag;

public class FornecedorFiltroDTO {

	private int idFornecedor;
	private String razaoNome;
	private String cnpjCpf;
	private int idMunicipio;
	private int limite;
	private int pagina;

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
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

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(int idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

}
