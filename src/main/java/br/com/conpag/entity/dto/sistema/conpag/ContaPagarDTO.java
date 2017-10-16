package br.com.conpag.entity.dto.sistema.conpag;

import java.util.Date;

public class ContaPagarDTO {

	private int id;
	private String fornecedor;
	private String empresa;
	private Date dtEmissao;
	private String descricao;
	private double saldoAnterior;
	private double emCaixa;
	private double paraPagar;
	private double valorPago;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(double saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public double getParaPagar() {
		return paraPagar;
	}

	public void setParaPagar(double paraPagar) {
		this.paraPagar = paraPagar;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public double getEmCaixa() {
		return emCaixa;
	}

	public void setEmCaixa(double emCaixa) {
		this.emCaixa = emCaixa;
	}

}
