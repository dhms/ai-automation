package br.com.cotiinformatica.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Cliente;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, UUID> {

	Cliente findByEmail(String email);
}
