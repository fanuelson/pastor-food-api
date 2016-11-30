package com.foundation.dao;

import org.springframework.data.repository.CrudRepository;

import com.foundation.model.Cliente;

public interface ClienteDAO extends CrudRepository<Cliente, Long> {

	
}
