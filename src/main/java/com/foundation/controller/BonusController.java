package com.foundation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.foundation.dto.BasicResponseDTO;
import com.foundation.dto.DarBonusRequestDTO;
import com.foundation.model.Bonus;
import com.foundation.service.BonusService;

@RestController
@RequestMapping("/api/bonus")
@RequestScope
public class BonusController {

	@Autowired
	private BonusService bonusService;
	
	@PutMapping(path="/darBonus")
	public ResponseEntity<?> darBonus(@RequestBody DarBonusRequestDTO darBonusRequestDTO) {
		Bonus bonus = bonusService.darBonus(darBonusRequestDTO.getIdCliente(), darBonusRequestDTO.getIdProduto());
		BasicResponseDTO basicResponseDTO = new BasicResponseDTO(bonus,"Registro salvo com sucesso.");
		return ResponseEntity.status(HttpStatus.CREATED).body(basicResponseDTO);
	}
	
}
