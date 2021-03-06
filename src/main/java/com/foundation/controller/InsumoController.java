package com.foundation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foundation.dto.BasicResponseDTO;
import com.foundation.exception.ValidacaoException;
import com.foundation.exporter.DocumentExportedVO;
import com.foundation.filtroConsulta.FiltroConsultaInsumo;
import com.foundation.model.Insumo;
import com.foundation.service.InsumoService;
import com.foundation.utils.ResponseEntityUtils;
import com.foundation.validacao.Validacoes;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController extends AbstractController {

	private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
	
	@Autowired
	private InsumoService insumoService;
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> findOne(@PathVariable Long id) {
		try {
			BasicResponseDTO basicResponse = new BasicResponseDTO(insumoService.findOne(id));
			return ResponseEntity.status(HttpStatus.CREATED).body(basicResponse);
		} catch (ValidacaoException e) {
			return new ResponseEntity<Validacoes>(e.getValidacoes(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public List<Insumo> findAll() {
		return insumoService.findAll();
	}

	@GetMapping(path = "/page")
	public Page<Insumo> findAll(Pageable page) {
		return insumoService.findAll(page);
	}
	
	@PostMapping(path = "/page/filterBy")
	public Page<Insumo> findByFiltro(@RequestBody FiltroConsultaInsumo filtro, Pageable page) {
		return insumoService.findByFilter(filtro, page);
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Insumo insumo) {
		BasicResponseDTO basicResponse = new BasicResponseDTO(insumoService.save(insumo), "Registro Salvo com sucesso.");
		return ResponseEntity.status(HttpStatus.CREATED).body(basicResponse);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try{
			insumoService.delete(id);
			BasicResponseDTO basicResponse = new BasicResponseDTO("Registro Removido com sucesso.");
			return ResponseEntity.status(HttpStatus.OK).body(basicResponse);
		} catch (ValidacaoException e) {
			return new ResponseEntity<Validacoes>(e.getValidacoes(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/relatorio", produces = "application/pdf")
	public ResponseEntity<InputStreamResource> downloadPDFFile() {
		
	    DocumentExportedVO doc = insumoService.createDocumentReportInsumo();
	    
	    return ResponseEntity
	            .ok()
	            .headers(ResponseEntityUtils.getHeaders("relatorio_insumos.pdf"))
	            .contentType(MediaType.parseMediaType(APPLICATION_OCTET_STREAM))
	            .contentLength(doc.getByteArray().length)
	            .body(new InputStreamResource(doc.getInputStream()));
	}
}
