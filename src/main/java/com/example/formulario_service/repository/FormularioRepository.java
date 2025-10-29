package com.example.formulario_service.repository;

import com.example.formulario_service.model.Formulario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FormularioRepository extends ReactiveMongoRepository<Formulario, String> {

    Mono<Boolean> existsByNumeroDocumento(String numeroDocumento);

    Mono<Formulario> findByNumeroDocumento(String numeroDocumento);
}
