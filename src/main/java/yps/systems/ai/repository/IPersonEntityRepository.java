package yps.systems.ai.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import yps.systems.ai.model.PersonEntity;

@Repository
public interface IPersonEntityRepository extends ReactiveCrudRepository<PersonEntity, Long> {

    Mono<Boolean> existsPersonEntityByEmail(String email);

    Mono<Boolean> existsPersonEntityByPhone(String phone);

}
