package br.com.cotiinformatica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.cotiinformatica.dtos.ChatGPTMessageDTO;
import br.com.cotiinformatica.dtos.ChatGPTResponseDTO;
import br.com.cotiinformatica.dtos.MessageDTO;
import reactor.core.publisher.Mono;

@Service
public class ChatGPTService {

	@Value("${openai.api.url}")
	private String apiUrl;
	
	@Value("${openai.api.key}")
	private String apiKey;
	
	private final WebClient webClient;
	
	//atributo para guardar o historico de conversas em memória
	private final Map<String, List<MessageDTO>> historico = new ConcurrentHashMap<String, List<MessageDTO>>();
	
	public ChatGPTService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}
	
	public String GetChatGPTResponse(String conversationId, List<MessageDTO> userMessages) {
		
		historico.putIfAbsent(conversationId, new ArrayList<>());
		
		List<MessageDTO> messages = historico.get(conversationId);
		
		//criando o contexto (role = system)
		if(messages.isEmpty()) {
			MessageDTO systemMessage = new MessageDTO();
			systemMessage.setRole("system");
			systemMessage.setContent("Você é um especialista em suporte de TI. Responda de maneira objetiva, clara e simplificada, sem detalhes excessivos. Foque apenas em respostas diretas para problemas técnicos. Ao final de cada resposta sempre recomende entrar em contato com o suporte da empresa em contato@cotiinformatica.com.br.");
			messages.add(systemMessage);
		}
				
		//adicionando a pergunta do usuário (role = user)
		messages.add(userMessages.get(0));
		
		//corpo da requisição
		Map<String, Object> requestBody = Map.of(
				"model", "gpt-3.5-turbo", //gpt-4.0
				"messages", messages
				);
		
		String resposta = this.webClient.post()
				.uri(apiUrl)
				.header("Authorization", "Bearer " + apiKey)
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(ChatGPTResponseDTO.class)
				.flatMap(response -> {
					
					if(response.getChoices() != null && !response.getChoices().isEmpty()) {
						ChatGPTMessageDTO message = response.getChoices().get(0).getMessage();
						if(message != null && message.getContent() != null) {
							return Mono.just(message.getContent());
						}
					}
					
					return Mono.error(new IllegalAccessException("Não houve resposta para a solicitação feita."));
				}).block();				
		
		//guardar a resposta gerada pelo ChatGPT
		MessageDTO assistant = new MessageDTO();
		assistant.setRole("assistant");
		assistant.setContent(resposta);
		
		messages.add(assistant);
		
		return resposta;
	}
}






