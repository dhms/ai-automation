package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AtendimentoRequestDTO;
import br.com.cotiinformatica.dtos.AtendimentoResponseDTO;
import br.com.cotiinformatica.services.AtendimentoService;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

	@Autowired AtendimentoService atendimentoService;
	
	@PostMapping
	public AtendimentoResponseDTO post(@RequestBody AtendimentoRequestDTO request) {
		return atendimentoService.criarAtendimento(request);
	}
}
