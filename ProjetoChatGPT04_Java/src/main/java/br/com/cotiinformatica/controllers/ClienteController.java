package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ClienteRequestDTO;
import br.com.cotiinformatica.dtos.ClienteResponseDTO;
import br.com.cotiinformatica.services.ClienteService;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired ClienteService clienteService;
	
	@PostMapping
	public ClienteResponseDTO post(@RequestBody ClienteRequestDTO request) {
		return clienteService.cadastrar(request);
	}
}
