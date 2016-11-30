package com.foundation.validador;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.foundation.model.Bonus;
import com.foundation.model.Cliente;
import com.foundation.model.Produto;

@Component
@RequestScope
public class ValidadorBonusBuilder extends AbstractValidadorBuilder{

	public static ValidadorBonusBuilder newInstance(){
		return new ValidadorBonusBuilder();
	}
	
	public ValidadorBonusBuilder validarBonusNaoEncontrado(Bonus bonus) {
		if(bonus == null) {
			getValidacoes().adicionarValidacaoRegraNegocio("Bônus não encontrado.");
		}
		return this;
	}
	
	public ValidadorBonusBuilder validarDarBonus(Long idProduto, Long idCliente) {
		if(idProduto == null) {
			getValidacoes().adicionarValidacaoCampoObrigatorio("produto", "Produto é Obrigatório.");
		}
		if(idCliente == null) {
			getValidacoes().adicionarValidacaoCampoObrigatorio("produto", "Cliente é Obrigatório.");
		}
		return this;
	}
	
	public ValidadorBonusBuilder validarDarBonus(Cliente cliente, Produto produto) {
		if(produto == null) {
			getValidacoes().adicionarValidacaoCampoObrigatorio("produto", "Produto é Obrigatório.");
		}
		if(cliente == null) {
			getValidacoes().adicionarValidacaoCampoObrigatorio("produto", "Cliente é Obrigatório.");
		}
		return this;
	}
	
}
