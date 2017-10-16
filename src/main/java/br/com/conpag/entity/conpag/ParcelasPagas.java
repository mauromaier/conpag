package br.com.conpag.entity.conpag;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="mod_conpag_parcelas_pagas")
public class ParcelasPagas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cd_id")
	private int id;
	
	private double valor;
	
	@Column(name="cd_conta")
	private int idConta;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_vencimento")
	private Calendar dtVencimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_pagamento")
	private Calendar dtPagamento;
	
	@Column(name="cd_parcela")
	private int idParcela;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getIdConta() {
		return idConta;
	}

	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}

	public Calendar getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Calendar dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Calendar getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Calendar dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public int getIdParcela() {
		return idParcela;
	}

	public void setIdParcela(int idParcela) {
		this.idParcela = idParcela;
	}
	
	
}
