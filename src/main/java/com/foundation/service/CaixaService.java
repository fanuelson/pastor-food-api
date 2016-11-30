package com.foundation.service;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foundation.annotation.NeedsCaixa;
import com.foundation.dao.CaixaDAO;
import com.foundation.dto.CaixaDTO;
import com.foundation.enums.CaixaStatus;
import com.foundation.filtroConsulta.FiltroConsultaCaixa;
import com.foundation.model.Caixa;
import com.foundation.validador.ValidadorCaixaBuilder;

@Service
public class CaixaService extends AbstractService{

	@Autowired
	private CaixaDAO caixaDAO;
	
	public Caixa findCaixaAberto() {
		return caixaDAO.findCaixaAberto();
	}
	
	public Page<CaixaDTO> findAllPageable(FiltroConsultaCaixa filtro, Pageable page) {
		return caixaDAO.findAllPageable(filtro, page);
	}
	
	public boolean existeCaixaAberto() {
		return findCaixaAberto() != null;
	}
	
	@NeedsCaixa(status = CaixaStatus.FECHADO)
	public Caixa abrirCaixa(BigDecimal valorInicial) {
		ValidadorCaixaBuilder.newInstance()
			.validarAbrirCaixa(valorInicial);
		Caixa caixaAberto = Caixa.abrir(DateTime.now().toDate(), valorInicial);
		return caixaDAO.save(caixaAberto);
	}
	
	@NeedsCaixa(status = CaixaStatus.ABERTO)
	public Caixa fecharCaixa() {
		Caixa caixa = findCaixaAberto();
		caixa.fechar();
		return caixaDAO.save(caixa);
	}
}
