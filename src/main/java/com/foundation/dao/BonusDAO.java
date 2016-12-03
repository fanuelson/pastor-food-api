package com.foundation.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foundation.model.Bonus;
import com.foundation.model.Cliente;

public interface BonusDAO extends CrudRepository<Bonus, Long> {

	List<Bonus> findAllBonusByCliente(Cliente cliente);
	
	@Query(" SELECT b FROM Bonus b "
			+ " WHERE b.cliente.id = ?1 "
			+ " AND b.produto.idProduto = ?2 ")
	Bonus findOneByClienteAndProduto(Long idCliente, Long idProduto);

	@Query(" SELECT b FROM Bonus b "
			+ " WHERE b.cliente.id = ?1 ")
	Page<Bonus> findAllByCliente(Long idCliente, Pageable page);

	@Modifying
	@Query("DELETE FROM Bonus b WHERE b.cliente.id = ?1")
	void deleteAllByCliente(Long idCliente);
}
