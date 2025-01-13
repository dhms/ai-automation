package br.com.cotiinformatica.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Atendimento;

@Repository
public interface AtendimentoRepository extends MongoRepository<Atendimento, UUID> {

	@Query(
			value = "{ 'emailCliente' : ?0 }",
			sort = "{ 'dataHoraAtendimento' : -1 }"
	)
	List<Atendimento> find(String emailCliente);
}
