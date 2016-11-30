package com.foundation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foundation.dao.ClienteDAO;
import com.foundation.model.Cliente;

@Service
public class ClienteService extends AbstractService{

	@Autowired
	private ClienteDAO clienteDAO;
	
	public Iterable<Cliente> findAll() {
		return clienteDAO.findAll();
	}
	
	public Cliente save(Cliente cliente) {
		return clienteDAO.save(cliente);
	}
	
	public void delete(Long idCliente) {
		clienteDAO.delete(idCliente);
	}
	
	public Cliente findOne(Long id) {
		return clienteDAO.findOne(id);
	}
}
