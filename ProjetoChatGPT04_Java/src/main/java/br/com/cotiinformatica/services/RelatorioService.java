package br.com.cotiinformatica.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.cotiinformatica.components.RabbitMQProducerComponent;
import br.com.cotiinformatica.dtos.ChatGPTMessageDTO;
import br.com.cotiinformatica.dtos.ChatGPTResponseDTO;
import br.com.cotiinformatica.dtos.MessageDTO;
import br.com.cotiinformatica.entities.Atendimento;
import br.com.cotiinformatica.repositories.AtendimentoRepository;
import reactor.core.publisher.Mono;

@Service
public class RelatorioService {

	@Autowired AtendimentoRepository atendimentoRepository;
	@Autowired RabbitMQProducerComponent rabbitMQProducerComponent;
	
	@Value("${openai.api.url}")
	private String apiUrl;
	
	@Value("${openai.api.key}")
	private String apiKey;
	
	private final WebClient webClient;	
	
	public RelatorioService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}
	
	public String gerarRelatorio(Date dataMin, Date dataMax) {
		
		//consultando todos os atendimentos por periodo de data
		var atendimentos = atendimentoRepository.findByDataAtendimento(dataMin, dataMax);
		
		//treinar o prompt (contexto) para geração do relatório
		var mensagens = new ArrayList<MessageDTO>();
		
		//primeiro, precisamos criar o contexto
		var contexto = new MessageDTO();
		contexto.setRole("system");
		contexto.setContent("Você é um gerador de relatórios analíticos, pegue as informações fornecidades dos históricos de atendimento de uma agência de viagens e descreva o número de atendimentos realizados, informe os destinos de viagem mais procurados e informe as dúvidas mais frequentes (passeios, hotéis etc.) e informe se houve alguma insatisfação de clientes no atendimento e se sim, e informe quais clientes ficaram insatisfeitos.");
		mensagens.add(contexto);
		
		//em seguida, vamos enviar os dados para análise
		var conteudo = new MessageDTO();
		conteudo.setRole("user");
		conteudo.setContent(atendimentos.stream().map(Atendimento::getConversa).collect(Collectors.joining(" | ")));
		mensagens.add(conteudo);
		
		Map<String, Object> requestBody = Map.of(
				"model", "gpt-3.5-turbo", 
				"messages", mensagens
				);
		
		//enviando para o ChatGPT
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
		
		try {
			//enviando a resposta para a mensageria
			rabbitMQProducerComponent.send(resposta);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return "Relatório gerado com sucesso. A análise será enviada para o email do gestor da empresa.";
	}
}
