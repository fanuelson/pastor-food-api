package com.foundation.dto;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.foundation.model.Caixa;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CaixaDTO {

	private Long id;

	private String dataAbertura;

	private String dataFechamento;

	private BigDecimal valorInicial;

	private BigDecimal valorAtual;

	private BigDecimal valorFinal;

	public CaixaDTO() {
	}

	public CaixaDTO(Caixa caixa) {
		this.id = caixa.getId();
		if (caixa.getDataAbertura() != null) {
			this.dataAbertura = new DateTime(caixa.getDataAbertura()).toString("dd/MM/yyyy HH:mm");
		}
		if (caixa.getDataFechamento() != null) {
			this.dataFechamento = new DateTime(caixa.getDataFechamento()).toString("dd/MM/yyyy HH:mm");
		}
		this.valorInicial = caixa.getValorInicial();
		this.valorAtual = caixa.getValorAtual();
		this.valorFinal = caixa.getValorFinal();
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
		CaixaDTO other = (CaixaDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
