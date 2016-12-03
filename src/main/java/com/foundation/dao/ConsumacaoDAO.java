package com.foundation.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.foundation.dto.ConsumacaoDTO;
import com.foundation.model.Consumacao;

public interface ConsumacaoDAO extends CrudRepository<Consumacao, Long> {

	@SuppressWarnings("all")
	@Query(" SELECT new com.foundation.dto.ConsumacaoDTO(cons) FROM Consumacao cons "
			+ " WHERE cons.cliente.id = ?1 "
			+ " GROUP BY cons.dataConsumacao"
			+ " ORDER BY cons.dataConsumacao DESC")
	Page<ConsumacaoDTO> findAllByCliente(Long idCliente, Pageable page);
	
	@Query(" SELECT c FROM Consumacao c"
			+ " WHERE c.cliente.id = ?1"
			+ " AND c.produto.idProduto = ?2")
	Consumacao findOneByClienteAndProduto(Long idCliente, Long idProduto);
	
	@Modifying
	@Query("DELETE FROM Consumacao c WHERE c.cliente.id = ?1")
	void deleteAllByCliente(Long idCliente);
}
