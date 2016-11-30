package com.foundation.validador;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class ValidadorCaixaBuilder extends AbstractValidadorBuilder{

	public static ValidadorCaixaBuilder newInstance() {
		return new ValidadorCaixaBuilder();
	}
	
	public ValidadorCaixaBuilder validarAbrirCaixa(BigDecimal valorInicial) {
		validarCampoObrigatorio("valorInicial", valorInicial);
		if(valorInicial!=null){
			if(valorInicial.compareTo(BigDecimal.ZERO) < 0){
				getValidacoes().adicionarValidacaoRegraNegocio("Valor Inicial não pode ser menor que 0.");
			}
		}
		return this;
	}
}
