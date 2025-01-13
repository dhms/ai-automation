package br.com.cotiinformatica.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.cotiinformatica.dtos.AtendimentoRequestDTO;
import br.com.cotiinformatica.dtos.AtendimentoResponseDTO;
import br.com.cotiinformatica.dtos.ChatGPTMessageDTO;
import br.com.cotiinformatica.dtos.ChatGPTResponseDTO;
import br.com.cotiinformatica.dtos.MessageDTO;
import br.com.cotiinformatica.entities.Atendimento;
import br.com.cotiinformatica.repositories.AtendimentoRepository;
import br.com.cotiinformatica.repositories.ClienteRepository;
import reactor.core.publisher.Mono;

@Service
public class AtendimentoService {

	@Autowired ClienteRepository clienteRepository;
	@Autowired AtendimentoRepository atendimentoRepository;
	
	@Value("${openai.api.url}")
	private String apiUrl;
	
	@Value("${openai.api.key}")
	private String apiKey;
	
	private final WebClient webClient;
	
	public AtendimentoService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}
	
	public AtendimentoResponseDTO criarAtendimento(AtendimentoRequestDTO request) {
		
		//trazer os dados do cliente do atendimento
		var cliente = clienteRepository.findByEmail(request.getEmailCliente());
		if(cliente == null)
			throw new IllegalArgumentException("Cliente não encontrado. Faça o seu cadastro primeiro para depois gerar atendimentos.");
		
		//criar a lista de mensagens que serão enviadas para o ChatGPT
		var mensagens = new ArrayList<MessageDTO>();
		
		//primeiro, precisamos criar o contexto
		var contexto = new MessageDTO();
		contexto.setRole("system");
		contexto.setContent("Você é um atendente de agência de viagens especializado em fornecer informações para os clientes sobre pacotes, hotéis, vôos e passeis turisticos. O cliente que você está atendendo é o " + cliente.getNome() + " e você sabe as seguintes informações deste cliente: " + cliente.getInformacoes() + ". Faça o atendimento de forma humanizada e cordial, tratando o cliente sempre pelo seu nome.");
		mensagens.add(contexto);
		
		//consultando até os 3 ultimos atendimentos que este cliente já teve com a IA
		var atendimentos = atendimentoRepository.findByEmailCliente(cliente.getEmail()).stream()
							.sorted((a1, a2) -> a2.getDataHoraAtendimento().compareTo(a1.getDataHoraAtendimento()))
							.limit(3)
							.map(Atendimento::getConversa)
							.collect(Collectors.joining(", "));
		
		//adicionando o 'assistant' com o histórico de atendimentos
		var historico = new MessageDTO();
		historico.setRole("assistant");
		historico.setContent(atendimentos);
		mensagens.add(historico);
		
		//adicionando a pergunta do cliente
		var pergunta = new MessageDTO();
		pergunta.setRole("user");
		pergunta.setContent(request.getTexto());
		mensagens.add(pergunta);
		
		//montando a requisição:
		Map<String, Object> requestBody = Map.of(
				"model", "gpt-3.5-turbo", 
				"messages", mensagens
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
		
		//gravar o atendimento no banco de dados
		Atendimento atendimento = new Atendimento();
		atendimento.setId(UUID.randomUUID());
		atendimento.setDataHoraAtendimento(Instant.now());
		atendimento.setEmailCliente(cliente.getEmail());
		atendimento.setConversa(resposta);
		
		atendimentoRepository.save(atendimento);
		
		//DTO de resposta
		AtendimentoResponseDTO response = new AtendimentoResponseDTO();
		response.setId(atendimento.getId());
		response.setDataHoraAtendimento(atendimento.getDataHoraAtendimento());
		response.setResposta(resposta);
		
		return response;
	}
}





