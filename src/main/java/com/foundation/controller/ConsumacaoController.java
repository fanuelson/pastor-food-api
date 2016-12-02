package com.foundation.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.foundation.dto.BasicResponseDTO;
import com.foundation.dto.RegistraConsumacaoRequestDTO;
import com.foundation.model.Consumacao;
import com.foundation.service.ConsumacaoService;

@RestController
@RequestMapping("/api/consumacoes")
@RequestScope
public class ConsumacaoController {

	@Autowired
	private ConsumacaoService consumacaoService;
	
	@PostMapping(path = "/registrar")
	public ResponseEntity<?> registrarConsumacao(@RequestBody RegistraConsumacaoRequestDTO registraConsumacaoRequestDTO) {
		BigDecimal quantidadeConsumida = registraConsumacaoRequestDTO.getQuantidadeConsumida();
		Long idProduto = registraConsumacaoRequestDTO.getIdProduto();
		Long idCliente = registraConsumacaoRequestDTO.getIdCliente();
		boolean fiado = registraConsumacaoRequestDTO.isFiado();
		Consumacao consumacao = consumacaoService.registrarConsumacao(quantidadeConsumida, idProduto, idCliente, fiado);
		BasicResponseDTO basicResponse = new BasicResponseDTO(consumacao, "Registro Salvo com sucesso.");
		return ResponseEntity.status(HttpStatus.CREATED).body(basicResponse);
	}
	
	@GetMapping(path = "/{idCons}/pagarDivida")
	public ResponseEntity<?> pagarDivida(@PathVariable(name = "idCons") Long idConsumacao) {
		Consumacao consumacao = consumacaoService.alterarParaPago(idConsumacao);
		BasicResponseDTO basicResponse = new BasicResponseDTO(consumacao, "Consumação paga com sucesso.");
		return ResponseEntity.status(HttpStatus.CREATED).body(basicResponse);
	}
}
