package com.foundation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.foundation.dto.BasicResponseDTO;
import com.foundation.dto.ConsumacaoDTO;
import com.foundation.model.Bonus;
import com.foundation.model.Cliente;
import com.foundation.service.BonusService;
import com.foundation.service.ClienteService;
import com.foundation.service.ConsumacaoService;

@RestController
@RequestMapping("/api/clientes")
@RequestScope
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ConsumacaoService consumacaoService;
	
	@Autowired
	private BonusService bonusService;

	@GetMapping
	public Iterable<Cliente> findAll() {
		return clienteService.findAll();
	}
	
	@GetMapping(path="/{id}/consumacoes")
	public Page<ConsumacaoDTO> findAllConsumacoesByCliente(@PathVariable(name="id") Long idCliente, Pageable page) {
		return consumacaoService.findAllByCliente(idCliente, page);
	}
	
	@GetMapping(path="/{id}/bonus")
	public Page<Bonus> findAllBonusByCliente(@PathVariable(name="id") Long idCliente, Pageable page) {
		return bonusService.findAllByCliente(idCliente, page);
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public ResponseEntity<?> save(@RequestBody Cliente cliente) {
		BasicResponseDTO basicResponseDTO = new BasicResponseDTO(clienteService.save(cliente),"Registro salvo com sucesso.");
		return ResponseEntity.status(HttpStatus.CREATED).body(basicResponseDTO);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		clienteService.delete(id);
		return ResponseEntity.ok(new BasicResponseDTO("Registro removido com sucesso."));
	}
	
}
