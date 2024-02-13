package yps.systems.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yps.systems.ai.model.PersonEntity;
import yps.systems.ai.model.PersonNode;
import yps.systems.ai.repository.IPersonEntityRepository;
import yps.systems.ai.repository.IPersonNodeRepository;

@RestController
@RequestMapping("/personService")
public class PersonController {

    @Autowired
    private IPersonNodeRepository nodeRepository;

    @Autowired
    private IPersonEntityRepository repository;

    @GetMapping("/findAll")
    public Flux<PersonEntity> findAll() {
        return repository.findAll();
    }

    @GetMapping("/findById/{idPerson}")
    public Mono<PersonEntity> findById(@PathVariable Long idPerson) {
        return repository.findById(idPerson)
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just(new PersonEntity());
                });
    }

    @PostMapping("/save")
    public Mono<PersonEntity> save(@RequestBody PersonEntity personEntity) {
        Mono<Boolean> isEmailTakenMono = repository.existsPersonEntityByEmail(personEntity.getEmail());
        Mono<Boolean> isPhoneTakenMono = repository.existsPersonEntityByPhone(personEntity.getPhone());
        return Mono.zip(isEmailTakenMono, isPhoneTakenMono)
                .flatMap(objects -> {
                    boolean isEmailTaken = objects.getT1();
                    boolean isPhoneTaken = objects.getT2();
                    if (isEmailTaken || isPhoneTaken) {
                        return Mono.error(new RuntimeException("Email or phone is already taken"));
                    }
                    return repository.save(personEntity)
                            .flatMap(personSaved -> nodeRepository
                                    .save(new PersonNode(personSaved.getIdPerson()))
                                    .thenReturn(personSaved)
                            );
                })
                .onErrorResume(error -> {
                    System.out.println("Error saving person: " + error.getMessage());
                    return Mono.just(new PersonEntity());
                });
    }

    @DeleteMapping("/delete")
    public Mono<Boolean> delete(@RequestBody PersonEntity personEntity) {
        return repository.findById(personEntity.getIdPerson())
                .flatMap(personFounded -> repository
                        .delete(personFounded)
                        .then(nodeRepository.deletePersonNodeByIdPerson(personFounded.getIdPerson()))
                        .thenReturn(true)
                )
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just(false);
                });
    }

    @DeleteMapping("/deleteById/{idPerson}")
    public Mono<Boolean> deleteById(@PathVariable Long idPerson) {
        return repository.findById(idPerson)
                .flatMap(personFounded -> repository
                        .delete(personFounded)
                        .then(nodeRepository.deletePersonNodeByIdPerson(personFounded.getIdPerson()))
                        .thenReturn(true)
                )
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just(false);
                });
    }

    @PutMapping("/update/{idPerson}")
    public Mono<PersonEntity> update(@PathVariable Long idPerson, @RequestBody PersonEntity personEntity) {
        return repository.findById(idPerson)
                .flatMap(personFounded -> {
                    personEntity.setIdPerson(personFounded.getIdPerson());
                    return repository.save(personEntity);
                })
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just(new PersonEntity());
                });
    }

}
