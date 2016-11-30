package com.foundation.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.foundation.dto.BasicResponseDTO;
import com.foundation.dto.CaixaDTO;
import com.foundation.filtroConsulta.FiltroConsultaCaixa;
import com.foundation.model.Caixa;
import com.foundation.service.CaixaService;

@RestController
@RequestMapping("/api/caixas")
@RequestScope
public class CaixaController {

	@Autowired
	private CaixaService caixaService;

	@RequestMapping(path = "/abrir" , method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<?> abrirCaixa(@RequestBody BigDecimal valorInicial) {
		BasicResponseDTO basicResponseDTO = new BasicResponseDTO(caixaService.abrirCaixa(valorInicial), "Registro salvo com sucesso.");
		return ResponseEntity.status(HttpStatus.CREATED).body(basicResponseDTO);
	}
	
	@GetMapping(path = "/aberto")
	public ResponseEntity<?> findCaixaAberto() {
		Caixa caixaAberto = caixaService.findCaixaAberto();
		BasicResponseDTO basicResponseDTO = new BasicResponseDTO();
		if(caixaAberto!=null){
			basicResponseDTO.setObj(new CaixaDTO(caixaAberto));
		}
		return ResponseEntity.status(HttpStatus.OK).body(basicResponseDTO);
	}

	@GetMapping(path = "/fechar")
	public ResponseEntity<?> fecharCaixa() {
		BasicResponseDTO basicResponseDTO = new BasicResponseDTO(caixaService.fecharCaixa(), "Registro salvo com sucesso.");
		return ResponseEntity.status(HttpStatus.OK).body(basicResponseDTO);
	}
	
	@PostMapping(path = "/filtered/pageable")
	public Page<CaixaDTO> findAllPageable(@RequestBody FiltroConsultaCaixa filtro, Pageable page) {
		return caixaService.findAllPageable(filtro, page);
	}
	
}
