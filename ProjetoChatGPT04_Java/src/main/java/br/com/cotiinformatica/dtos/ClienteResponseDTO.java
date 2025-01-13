package br.com.cotiinformatica.dtos;

import java.time.Instant;
import java.util.UUID;

public class ClienteResponseDTO {

	private UUID id;
	private Instant dataHoraCadastro;
	private String message;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Instant getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(Instant dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
