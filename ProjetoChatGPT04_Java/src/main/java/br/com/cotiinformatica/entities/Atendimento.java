package br.com.cotiinformatica.entities;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "atendimentos")
public class Atendimento {

	@Id
	private UUID id;
	private String emailCliente;
	private String role;
	private Instant dataHoraAtendimento;
	private String conversa;

	public Atendimento() {
		// TODO Auto-generated constructor stub
	}

	public Atendimento(UUID id, String emailCliente, String role, Instant dataHoraAtendimento, String conversa) {
		super();
		this.id = id;
		this.emailCliente = emailCliente;
		this.role = role;
		this.dataHoraAtendimento = dataHoraAtendimento;
		this.conversa = conversa;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Instant getDataHoraAtendimento() {
		return dataHoraAtendimento;
	}

	public void setDataHoraAtendimento(Instant dataHoraAtendimento) {
		this.dataHoraAtendimento = dataHoraAtendimento;
	}

	public String getConversa() {
		return conversa;
	}

	public void setConversa(String conversa) {
		this.conversa = conversa;
	}

}
