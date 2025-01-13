package br.com.cotiinformatica.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.ClienteRequestDTO;
import br.com.cotiinformatica.dtos.ClienteResponseDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired ClienteRepository clienteRepository;
	
	public ClienteResponseDTO cadastrar(ClienteRequestDTO request) {
		
		var cliente = new Cliente();
		
		cliente.setId(UUID.randomUUID());
		cliente.setNome(request.getNome());
		cliente.setEmail(request.getEmail());
		cliente.setInformacoes(request.getInformacoes());
		
		clienteRepository.save(cliente);
		
		var response = new ClienteResponseDTO();
		response.setId(cliente.getId());
		response.setDataHoraCadastro(Instant.now());
		response.setMessage("Cadastro realizado com sucesso!");
		
		return response;
	}
}
