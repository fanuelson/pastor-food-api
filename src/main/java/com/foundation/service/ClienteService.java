package com.foundation.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foundation.dao.ClienteDAO;
import com.foundation.model.Cliente;
import com.foundation.validador.ValidadorClienteBuilder;

@Service
public class ClienteService extends AbstractService{

	@Autowired
	private ClienteDAO clienteDAO;
	
	@Autowired
	private ConsumacaoService consumacaoService;
	
	@Autowired
	private BonusService bonusService;
	
	public Iterable<Cliente> findAll() {
		return clienteDAO.findAll();
	}
	
	public Cliente save(Cliente cliente) {
		ValidadorClienteBuilder.newInstance()
			.validarCampoObrigatorioString("nome", cliente.getNome())
			.assertValid();
		return clienteDAO.save(cliente);
	}
	
	@Transactional
	public void delete(Long idCliente) {
		consumacaoService.deleteAllByCliente(idCliente);
		bonusService.deleteAllByCliente(idCliente);
		clienteDAO.delete(idCliente);
	}
	
	public Cliente findOne(Long id) {
		return clienteDAO.findOne(id);
	}
	
}
