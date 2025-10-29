package com.example.formulario_service.service;

import com.example.formulario_service.dto.FormularioRequest;
import com.example.formulario_service.dto.FormularioResponse;
import com.example.formulario_service.exception.FormularioAlreadyExistsException;
import com.example.formulario_service.model.Formulario;
import com.example.formulario_service.repository.FormularioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormularioService {

    private final FormularioRepository formularioRepository;

    public Mono<FormularioResponse> registrarFormulario(FormularioRequest request) {
        return formularioRepository.existsByNumeroDocumento(request.getNumeroDocumento())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new FormularioAlreadyExistsException(
                                "Ya existe un formulario registrado con el número de documento: " + request.getNumeroDocumento()));
                    }

                    Formulario formulario = request.toEntity();
                    formulario.setFechaCreacion(LocalDateTime.now());
                    formulario.setFechaActualizacion(LocalDateTime.now());

                    return formularioRepository.save(formulario)
                            .map(FormularioResponse::fromEntity)
                            .doOnSuccess(saved ->
                                    log.info("Formulario registrado exitosamente: {}", saved.getId()));
                })
                .doOnError(error -> log.error("Error al registrar formulario: {}", error.getMessage()));
    }

    public Flux<FormularioResponse> listarFormularios(int page, int size) {
        return formularioRepository.findAll()
                .skip((long) page * size)
                .take(size)
                .map(FormularioResponse::fromEntity)
                .doOnSubscribe(subscription ->
                        log.debug("Listando formularios - página: {}, tamaño: {}", page, size));
    }

    public Mono<FormularioResponse> obtenerFormularioPorId(String id) {
        return formularioRepository.findById(id)
                .map(FormularioResponse::fromEntity)
                .doOnSuccess(formulario ->
                        log.debug("Formulario encontrado: {}", id))
                .doOnError(error ->
                        log.error("Error al obtener formulario {}: {}", id, error.getMessage()));
    }
}