package com.example.formulario_service;

import com.example.formulario_service.dto.FormularioRequest;
import com.example.formulario_service.exception.FormularioAlreadyExistsException;
import com.example.formulario_service.model.Formulario;
import com.example.formulario_service.repository.FormularioRepository;
import com.example.formulario_service.service.FormularioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FormularioServiceTest {

    @Mock
    private FormularioRepository formularioRepository;

    @InjectMocks
    private FormularioService formularioService;

    private FormularioRequest formularioRequest;
    private Formulario formulario;

    @BeforeEach
    void setUp() {
        Map<String, Object> camposAdicionales = new HashMap<>();
        camposAdicionales.put("empresa", "Mi Empresa SA");
        camposAdicionales.put("cargo", "Desarrollador");

        formularioRequest = new FormularioRequest();
        formularioRequest.setNombre("Juan");
        formularioRequest.setApellido("Pérez");
        formularioRequest.setTipoDocumento("DNI");
        formularioRequest.setNumeroDocumento("12345678");
        formularioRequest.setCelular("+51987654321");
        formularioRequest.setCorreo("juan.perez@example.com");
        formularioRequest.setTratamientoDatos(true);
        formularioRequest.setCamposAdicionales(camposAdicionales);

        formulario = formularioRequest.toEntity();
        formulario.setId("507f1f77bcf86cd799439011");
        formulario.setFechaCreacion(LocalDateTime.now());
        formulario.setFechaActualizacion(LocalDateTime.now());
    }

    @Test
    void registrarFormulario_WhenFormularioDoesNotExist_ShouldCreateFormulario() {
        // Given
        when(formularioRepository.existsByNumeroDocumento(anyString())).thenReturn(Mono.just(false));
        when(formularioRepository.save(any(Formulario.class))).thenReturn(Mono.just(formulario));

        // When & Then
        StepVerifier.create(formularioService.registrarFormulario(formularioRequest))
                .expectNextMatches(response ->
                        response.getId().equals("507f1f77bcf86cd799439011") &&
                                response.getNumeroDocumento().equals("12345678") &&
                                response.getCamposAdicionales().containsKey("empresa"))
                .verifyComplete();
    }

    @Test
    void registrarFormulario_WhenFormularioExists_ShouldThrowException() {
        // Given
        when(formularioRepository.existsByNumeroDocumento(anyString())).thenReturn(Mono.just(true));

        // When & Then
        StepVerifier.create(formularioService.registrarFormulario(formularioRequest))
                .expectError(FormularioAlreadyExistsException.class)
                .verify();
    }

    @Test
    void listarFormularios_ShouldReturnFormularios() {
        // Given
        when(formularioRepository.findAll()).thenReturn(Flux.just(formulario));

        // When & Then
        StepVerifier.create(formularioService.listarFormularios(0, 10))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void obtenerFormularioPorId_WhenExists_ShouldReturnFormulario() {
        // Given
        when(formularioRepository.findById(anyString())).thenReturn(Mono.just(formulario));

        // When & Then
        StepVerifier.create(formularioService.obtenerFormularioPorId("507f1f77bcf86cd799439011"))
                .expectNextMatches(response ->
                        response.getId().equals("507f1f77bcf86cd799439011") &&
                                response.getNombre().equals("Juan"))
                .verifyComplete();
    }

    @Test
    void obtenerFormularioPorId_WhenNotExists_ShouldReturnEmpty() {
        // Given
        when(formularioRepository.findById(anyString())).thenReturn(Mono.empty());

        // When & Then
        StepVerifier.create(formularioService.obtenerFormularioPorId("non-existent"))
                .verifyComplete();
    }
}
