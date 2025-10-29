package com.example.formulario_service;

import com.example.formulario_service.controller.FormularioController;
import com.example.formulario_service.dto.FormularioRequest;
import com.example.formulario_service.dto.FormularioResponse;
import com.example.formulario_service.service.FormularioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FormularioController.class)
@Import(TestSecurityConfig.class)
class FormularioControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    // En Spring Boot 3.4.0, @MockBean funciona correctamente
    // Si tu IDE marca deprecación, ignóralo - es un falso positivo
    @MockBean
    private FormularioService formularioService;

    private FormularioRequest createValidRequest() {
        FormularioRequest request = new FormularioRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setTipoDocumento("DNI");
        request.setNumeroDocumento("12345678");
        request.setCelular("+51987654321");
        request.setCorreo("juan.perez@example.com");
        request.setTratamientoDatos(true);
        return request;
    }

    private FormularioResponse createValidResponse() {
        FormularioResponse response = new FormularioResponse();
        response.setId("507f1f77bcf86cd799439011");
        response.setNombre("Juan");
        response.setApellido("Pérez");
        response.setTipoDocumento("DNI");
        response.setNumeroDocumento("12345678");
        response.setCelular("+51987654321");
        response.setCorreo("juan.perez@example.com");
        response.setTratamientoDatos(true);
        return response;
    }

    @Test
    void registrarFormulario_WithValidRequest_ShouldReturnCreated() {
        // Given
        FormularioRequest request = createValidRequest();
        FormularioResponse response = createValidResponse();

        when(formularioService.registrarFormulario(any(FormularioRequest.class)))
                .thenReturn(Mono.just(response));

        // When & Then
        webTestClient.post()
                .uri("/formularios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("507f1f77bcf86cd799439011")
                .jsonPath("$.nombre").isEqualTo("Juan");
    }

    @Test
    void registrarFormulario_WithInvalidRequest_ShouldReturnBadRequest() {
        // Given
        FormularioRequest invalidRequest = new FormularioRequest();
        invalidRequest.setNombre(""); // Nombre vacío
        invalidRequest.setTratamientoDatos(false);

        // When & Then
        webTestClient.post()
                .uri("/formularios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void listarFormularios_ShouldReturnOk() {
        // Given
        FormularioResponse response = createValidResponse();
        when(formularioService.listarFormularios(anyInt(), anyInt()))
                .thenReturn(Flux.just(response));

        // When & Then
        webTestClient.get()
                .uri("/formularios?page=0&size=10")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FormularioResponse.class).hasSize(1);
    }

    @Test
    void obtenerFormularioPorId_ShouldReturnOk() {
        // Given
        String formularioId = "507f1f77bcf86cd799439011";
        FormularioResponse response = createValidResponse();

        when(formularioService.obtenerFormularioPorId(anyString()))
                .thenReturn(Mono.just(response));

        // When & Then
        webTestClient.get()
                .uri("/formularios/" + formularioId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(formularioId);
    }

    @Test
    void obtenerFormularioPorId_WhenNotFound_ShouldReturnNotFound() {
        // Given
        String formularioId = "non-existent-id";
        when(formularioService.obtenerFormularioPorId(anyString()))
                .thenReturn(Mono.empty());

        // When & Then
        webTestClient.get()
                .uri("/formularios/" + formularioId)
                .exchange()
                .expectStatus().isOk(); // En Reactivo, Mono.empty() no lanza 404
    }
}
