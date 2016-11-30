package com.foundation.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RegistraConsumacaoRequestDTO {

	private BigDecimal quantidadeConsumida;
	private Long idProduto;
	private Long idCliente;

}
