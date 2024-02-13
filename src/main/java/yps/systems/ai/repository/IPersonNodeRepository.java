package yps.systems.ai.repository;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import yps.systems.ai.model.PersonNode;

@Repository
public interface IPersonNodeRepository extends ReactiveNeo4jRepository<PersonNode, Long> {

    Mono<Void> deletePersonNodeByIdPerson(Long idPerson);

}
