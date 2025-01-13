package br.com.cotiinformatica.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.cotiinformatica.dtos.AgendaRequestDto;
import br.com.cotiinformatica.dtos.GeminiContentRequestDto;
import br.com.cotiinformatica.dtos.GeminiGenerateContentRequestDto;
import br.com.cotiinformatica.dtos.GeminiPartRequestDto;

@Service
public class GeminiService {

	@Value("${gemini.api.url}")
	private String apiUrl;
	
	@Value("${gemini.api.key}")
	private String apiKey;
	
	private final WebClient webClient;
	
	public GeminiService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}
	
	public String organizarAgenda(AgendaRequestDto request) {
	    
		var tarefas = "";
		for(var tarefa : request.getTarefas()) {
			tarefas += "Tarefa: " + tarefa.getNome() + ", horas necessárias: " + tarefa.getTempoEmHoras()
				    + ", complexidade: " + tarefa.getComplexidade() + ", urgência: " + tarefa.getUrgencia() + ". ";
		}
		
		//criando o contexto
	    var pergunta = "Planeje e organize a minha agenda de tarefas para o periodo de datas de: "
	    			 + request.getDataInicio() + " até " + request.getDataFim() + " "
	    			 + "e sabendo que a minha disponibilidade de trabalho é: " + request.getDescricao() + "."
	    			 + "Preciso que defina a melhor data de inicio, fim e horário para execução de cada tarefa, seguem: "
	    			 + tarefas;
	    			 
	    //organizando os DTOs
	    var part = new GeminiPartRequestDto();
	    part.setText(pergunta);

	    var parts = new ArrayList<GeminiPartRequestDto>();
	    parts.add(part);

	    var content = new GeminiContentRequestDto();
	    content.setParts(parts.toArray(new GeminiPartRequestDto[0]));

	    var contents = new ArrayList<GeminiContentRequestDto>();
	    contents.add(content);

	    var generate = new GeminiGenerateContentRequestDto();
	    generate.setContents(contents.toArray(new GeminiContentRequestDto[0]));

	    //enviando a requisição..
	    var resposta = webClient.post()
	        .uri(apiUrl + apiKey)
	        .bodyValue(generate)
	        .retrieve()
	        .bodyToMono(String.class)
	        .block();

	    return resposta;
	}
	
	public String realizarBusca(String busca) {
	    
	    //organizando os DTOs
	    var part = new GeminiPartRequestDto();
	    part.setText(busca);

	    var parts = new ArrayList<GeminiPartRequestDto>();
	    parts.add(part);

	    var content = new GeminiContentRequestDto();
	    content.setParts(parts.toArray(new GeminiPartRequestDto[0]));

	    var contents = new ArrayList<GeminiContentRequestDto>();
	    contents.add(content);

	    var generate = new GeminiGenerateContentRequestDto();
	    generate.setContents(contents.toArray(new GeminiContentRequestDto[0]));

	    //enviando a requisição..
	    var resposta = webClient.post()
	        .uri(apiUrl + apiKey)
	        .bodyValue(generate)
	        .retrieve()
	        .bodyToMono(String.class)
	        .block();

	    return resposta;
	}
}
