package br.com.cotiinformatica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.MessageDTO;
import br.com.cotiinformatica.services.ChatGPTService;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentosController {

	@Autowired ChatGPTService chatGPTService;
	
	@PostMapping("{conversationId}")
	public String post(@PathVariable String conversationId, @RequestBody List<MessageDTO> messages) {
		return chatGPTService.GetChatGPTResponse(conversationId, messages);
	}
}
