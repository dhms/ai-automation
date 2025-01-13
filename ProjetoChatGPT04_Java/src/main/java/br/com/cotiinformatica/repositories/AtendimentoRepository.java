package br.com.cotiinformatica.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Atendimento;

@Repository
public interface AtendimentoRepository extends MongoRepository<Atendimento, UUID> {

	List<Atendimento> findByEmailCliente(String emailCliente);
	
	@Query("{ 'dataHoraAtendimento' : { $gte : ?0, $lte : ?1 } }")
	List<Atendimento> findByDataAtendimento(Date dataInicio, Date dataFim);
}
