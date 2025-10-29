package com.example.formulario_service.controller;

import com.example.formulario_service.dto.FormularioRequest;
import com.example.formulario_service.dto.FormularioResponse;
import com.example.formulario_service.service.FormularioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/formularios")
@RequiredArgsConstructor
@Tag(name = "Formularios", description = "APIs para el registro y consulta de formularios")
public class FormularioController {

    private final FormularioService formularioService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar formulario", description = "Registra un nuevo formulario con los datos proporcionados")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Formulario registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = FormularioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "El formulario ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Mono<FormularioResponse> registrarFormulario(
            @Parameter(description = "Datos del formulario a registrar")
            @Valid @RequestBody FormularioRequest formularioRequest) {
        return formularioService.registrarFormulario(formularioRequest);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar formularios", description = "Obtiene la lista de formularios registrados (requiere API Key)")
    @SecurityRequirement(name = "ApiKeyAuth")  // ← AGREGAR ESTA LÍNEA
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de formularios obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado - API Key inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Flux<FormularioResponse> listarFormularios(
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "20") int size) {
        return formularioService.listarFormularios(page, size);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtener formulario por ID", description = "Obtiene un formulario específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Formulario encontrado"),
            @ApiResponse(responseCode = "404", description = "Formulario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Mono<FormularioResponse> obtenerFormularioPorId(
            @Parameter(description = "ID del formulario") @PathVariable String id) {
        return formularioService.obtenerFormularioPorId(id);
    }
}
