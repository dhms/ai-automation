package br.com.cotiinformatica.entities;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientes")
public class Cliente {

	@Id
	private UUID id;
	private String nome;
	private String email;
	private String informacoes;

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(UUID id, String nome, String email, String informacoes) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.informacoes = informacoes;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInformacoes() {
		return informacoes;
	}

	public void setInformacoes(String informacoes) {
		this.informacoes = informacoes;
	}

}
