package br.com.conpag.entity.conpag;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="mod_conpag_parcelas")
public class Parcelas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cd_id")
	private int id;
	
	private double valor;
	
	@JoinColumn(name = "cd_conta")
	@ManyToOne
	@JsonBackReference
	private ContaPagar conta;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_vencimento")
	private Calendar dtVencimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_pagamento")
	private Calendar dtPagamento;

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

	public ContaPagar getConta() {
		return conta;
	}

	public void setConta(ContaPagar conta) {
		this.conta = conta;
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
	
}
