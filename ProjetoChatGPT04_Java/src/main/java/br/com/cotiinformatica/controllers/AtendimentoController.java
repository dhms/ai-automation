package br.com.cotiinformatica.controllers;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AtendimentoRequestDTO;
import br.com.cotiinformatica.dtos.AtendimentoResponseDTO;
import br.com.cotiinformatica.services.AtendimentoService;
import br.com.cotiinformatica.services.RelatorioService;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

	@Autowired AtendimentoService atendimentoService;
	@Autowired RelatorioService relatorioService;
	
	@PostMapping
	public AtendimentoResponseDTO post(@RequestBody AtendimentoRequestDTO request) {
		return atendimentoService.criarAtendimento(request);
	}
	
	@GetMapping("{dataMin}/{dataMax}")
	public String get(@PathVariable String dataMin, @PathVariable String dataMax) throws Exception {
		
		var dtMin = new SimpleDateFormat("yyyy-MM-dd").parse(dataMin);
		var dtMax = new SimpleDateFormat("yyyy-MM-dd").parse(dataMax);
		
		return relatorioService.gerarRelatorio(dtMin, dtMax);
	}
}
