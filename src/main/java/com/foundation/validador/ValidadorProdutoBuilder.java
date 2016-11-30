package com.foundation.validador;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.foundation.model.Composicao;
import com.foundation.model.Insumo;
import com.foundation.model.Produto;

@Component
@RequestScope
public class ValidadorProdutoBuilder extends AbstractValidadorBuilder {

	static final String mensagem_quantidade_insumo_insuficiente = "Quantidade insuficiente do insumo %s: %s";
	
	public static ValidadorProdutoBuilder newInstance() {
		return new ValidadorProdutoBuilder();
	}
	
	public ValidadorProdutoBuilder validarQuantidadeInsumos(Produto produto, BigDecimal quantidade){
		List<Composicao> composicoes = produto.getComposicoes();
		for (Composicao composicao : composicoes) {
			Insumo insumo = composicao.getInsumo();
			if(!insumo.possuiQuantidade(quantidade)) {
				getValidacoes()
					.adicionarValidacaoCampoInvalido(insumo.getNome(), String.format(mensagem_quantidade_insumo_insuficiente, insumo.getNome(), insumo.getQuantidade().toString()));
			}
		}
		return this;
	}
}
