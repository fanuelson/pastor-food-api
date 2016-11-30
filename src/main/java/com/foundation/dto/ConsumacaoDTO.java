package com.foundation.dto;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.foundation.model.Consumacao;
import com.foundation.model.Produto;

import lombok.Data;

@Data
public class ConsumacaoDTO {

	private Long id;

	private BigDecimal quantidadeConsumida;

	private String dataConsumacao;

	private Produto produto;
	
	public ConsumacaoDTO() { }
	
	public ConsumacaoDTO(Consumacao consumacao) {
		this.id = consumacao.getId();
		this.quantidadeConsumida = consumacao.getQuantidadeConsumida();
		this.dataConsumacao = new DateTime(consumacao.getDataConsumacao()).toString("dd/MM/yyyy HH:mm");
		this.produto = consumacao.getProduto();
	}

}
