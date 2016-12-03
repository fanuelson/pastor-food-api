package com.foundation.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foundation.annotation.NeedsCaixa;
import com.foundation.dao.ConsumacaoDAO;
import com.foundation.dto.ConsumacaoDTO;
import com.foundation.enums.CaixaStatus;
import com.foundation.model.Caixa;
import com.foundation.model.Cliente;
import com.foundation.model.Consumacao;
import com.foundation.model.Produto;
import com.foundation.validador.ValidadorProdutoBuilder;

@Service
public class ConsumacaoService extends AbstractService{

	@Autowired
	private ConsumacaoDAO consumacaoDAO;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private BonusService bonusService;
	
	@Transactional
	@NeedsCaixa(status = CaixaStatus.ABERTO)
	public Consumacao registrarConsumacao(BigDecimal quantidadeConsumida, Long idProduto, Long idCliente, boolean fiado) {
		Cliente cliente = clienteService.findOne(idCliente);
		Produto produto = produtoService.findOneFetchComposicoes(idProduto);
		Caixa caixaAberto = caixaService.findCaixaAberto();

		ValidadorProdutoBuilder.newInstance()
			.validarQuantidadeInsumos(produto, quantidadeConsumida)
			.assertValid();
		
		if(!fiado) {
			caixaAberto.aumentarValorAtual(produto.getPrecoVenda().multiply(quantidadeConsumida));
			bonusService.aumentarOuInserirBonus(cliente, produto, quantidadeConsumida);
		}

		produto.gastarInsumos(quantidadeConsumida);
		
		Consumacao consumacao = new Consumacao(quantidadeConsumida, produto, cliente, new Date(), fiado);
		return consumacaoDAO.save(consumacao);
	}
	
	@NeedsCaixa(status = CaixaStatus.ABERTO)
	@Transactional
	public Consumacao alterarParaPago(Long idConsumacao) {
		Consumacao c = consumacaoDAO.findOne(idConsumacao);
		c.setDevendo(false);
		Caixa caixaAberto = caixaService.findCaixaAberto();
		caixaAberto.aumentarValorAtual(c.getProduto().getPrecoVenda().multiply(c.getQuantidadeConsumida()));
		bonusService.aumentarOuInserirBonus(c.getCliente(), c.getProduto(), c.getQuantidadeConsumida());
		
		return c;
	}
	
	@Transactional(value = TxType.NOT_SUPPORTED)
	public Page<ConsumacaoDTO> findAllByCliente(Long idCliente, Pageable page) {
		return consumacaoDAO.findAllByCliente(idCliente, page);
	}
	
	public void deleteAllByCliente(Long idCliente) {
		consumacaoDAO.deleteAllByCliente(idCliente);
	}
}
