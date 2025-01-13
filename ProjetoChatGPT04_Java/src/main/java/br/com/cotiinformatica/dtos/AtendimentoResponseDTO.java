package br.com.cotiinformatica.dtos;

import java.time.Instant;
import java.util.UUID;

public class AtendimentoResponseDTO {

	private UUID id;
	private Instant dataHoraAtendimento;
	private String resposta;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Instant getDataHoraAtendimento() {
		return dataHoraAtendimento;
	}

	public void setDataHoraAtendimento(Instant dataHoraAtendimento) {
		this.dataHoraAtendimento = dataHoraAtendimento;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

}
