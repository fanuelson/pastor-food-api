package com.foundation.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Caixa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "data_abertura")
	private Date dataAbertura;

	@Column(name = "data_fechamento")
	private Date dataFechamento;

	@Column(name = "valor_inicial")
	private BigDecimal valorInicial;

	@Column(name = "valor_atual")
	private BigDecimal valorAtual;

	@Column(name = "valor_final")
	private BigDecimal valorFinal;

	public Caixa() {}
	
	public static Caixa abrir(Date dataAbertura, BigDecimal valorInicial) {
		return new Caixa(dataAbertura, valorInicial);
	}
	
	public void fechar() {
		this.valorFinal = valorAtual;
		this.dataFechamento = DateTime.now().toDate();
	}
	
	public Caixa(Date dataAbertura, BigDecimal valorInicial) {
		this.dataAbertura = dataAbertura;
		this.valorInicial = valorInicial;
		this.valorAtual = this.valorInicial;
	}
	
	public void aumentarValorAtual(BigDecimal valorAumentar) {
		this.valorAtual = this.valorAtual.add(valorAumentar);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caixa other = (Caixa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
