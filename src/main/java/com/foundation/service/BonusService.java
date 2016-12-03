package com.foundation.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foundation.dao.BonusDAO;
import com.foundation.model.Bonus;
import com.foundation.model.Cliente;
import com.foundation.model.Produto;
import com.foundation.validador.ValidadorBonusBuilder;

@Service
public class BonusService extends AbstractService{

	@Autowired
	private BonusDAO bonusDAO;
	
	@Autowired
	private ProdutoService produtoService;
	
	public Page<Bonus> findAllByCliente(Long idCliente, Pageable page) {
		return bonusDAO.findAllByCliente(idCliente, page);
	}
	
	@Transactional
	public Bonus darBonus(Long idCliente, Long idProduto) {
		Bonus bonus = bonusDAO.findOneByClienteAndProduto(idCliente, idProduto);
		ValidadorBonusBuilder.newInstance()
			.validarBonusNaoEncontrado(bonus)
			.validarDarBonus(idProduto, idCliente)
			.assertValid();
		bonus.diminuirBonus(BigDecimal.TEN);
		Produto produto = produtoService.findOne(idProduto);
		produto.gastarInsumos(BigDecimal.ONE);
		return bonus;
	}
	
	public Bonus aumentarOuInserirBonus(Cliente cliente, Produto produto, BigDecimal quantidadeConsumida) {
		Bonus bonus = bonusDAO.findOneByClienteAndProduto(cliente.getId(), produto.getIdProduto());
		ValidadorBonusBuilder.newInstance()
			.validarDarBonus(cliente, produto)
			.assertValid();
		if(bonus == null) {
			bonus = new Bonus(cliente, produto);
			bonus.setQuantidadeBonusCorrente(BigDecimal.ZERO);
		}
		bonus.incrementarBonus(quantidadeConsumida);
		return bonusDAO.save(bonus);
	}
	
	public void deleteAllByCliente(Long idCliente) {
		bonusDAO.deleteAllByCliente(idCliente);
	}
}
