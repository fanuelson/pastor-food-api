package com.foundation.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.foundation.dto.CaixaDTO;
import com.foundation.filtroConsulta.FiltroConsultaCaixa;
import com.foundation.model.Caixa;

public interface CaixaDAO extends CrudRepository<Caixa, Long> {

	@Query("SELECT c FROM Caixa c WHERE c.dataFechamento IS NULL")
	public Caixa findCaixaAberto();
	
	@SuppressWarnings("all")
	@Query(" SELECT new com.foundation.dto.CaixaDTO(c) FROM Caixa c "
			+ " WHERE (:#{#filtro.idCaixa} IS NULL OR c.id = :#{#filtro.idCaixa}) "
			+ " AND (:#{#filtro.dataInicialHoraZerada} IS NULL OR c.dataAbertura >= :#{#filtro.dataInicialHoraZerada}) "
			+ " AND (:#{#filtro.dataFinalHoraFinalDia} IS NULL OR c.dataAbertura <= :#{#filtro.dataFinalHoraFinalDia}) ")
	public Page<CaixaDTO> findAllPageable(@Param("filtro") FiltroConsultaCaixa filtro, Pageable page);
}
