package com.foundation.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import com.foundation.dao.ProdutoDAO;
import com.foundation.filtroConsulta.FiltroConsultaProduto;
import com.foundation.model.Composicao;
import com.foundation.model.Produto;
import com.foundation.utils.CustomCollectionUtils;
import com.foundation.validador.AbstractValidadorBuilder;
import com.foundation.validador.ValidadorComposicaoBuilder;
import com.foundation.validador.ValidadorProdutoBuilder;

@Service
@RequestScope
public class ProdutoService {

	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private ComposicaoService composicaoService;

	public Produto findOne(Long idProduto) {
		return produtoDAO.findOne(idProduto);
	}
	
	public List<Produto> findAll() {
		return CustomCollectionUtils.toList(produtoDAO.findAll());
	}
	
	public Produto findOneFetchComposicoes(Long idProduto) {
		return produtoDAO.findOneFetchComposicoes(idProduto);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Produto> findAll(Pageable page) {
		return produtoDAO.findAll(page);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Produto> findByFilter(FiltroConsultaProduto filtro, Pageable page) {
		return produtoDAO.findByFilter(filtro, page);
	}

	public Produto save(Produto produto) {
		montarComposicoes(produto);
		validar(produto);
		removerComposicoes(produto);
		return produtoDAO.save(produto);
	}

	private void removerComposicoes(Produto produto) {
		if(produto.getIdProduto()!=null){
			Produto p = produtoDAO.findOne(produto.getIdProduto());
			composicaoService.removeAll(p.getComposicoes());
		}
	}

	private void validar(Produto produto) {

		AbstractValidadorBuilder vpb = ValidadorProdutoBuilder.newInstance()
			.validarCampoObrigatorio("nome", produto.getNome())
			.validarCampoObrigatorio("status", produto.getStatus())
			.validarCampoObrigatorio("precoVenda", produto.getPrecoVenda());
		
		ValidadorComposicaoBuilder.newInstance()
			.validarComposicoes(produto.getComposicoes())
			.adicionarValidacoes(vpb.getValidacoes())
			.assertValid();
	}
	
	public Produto toggleStatus(Long idProduto) {
		Produto prod = produtoDAO.findOne(idProduto);
		prod.toggleStatus();
		return produtoDAO.save(prod);
	}

	public void delete(Long id) {
		produtoDAO.delete(id);
	}
	
	public void delete(Produto produto) {
		produtoDAO.delete(produto);
	}
	
	private void montarComposicoes(Produto produto) {
		if(CollectionUtils.isNotEmpty(produto.getComposicoes())) {
			for (Composicao composicao : produto.getComposicoes()) {
				composicao.setProduto(produto);
			}
		}
	}

}