package com.example.formulario_service.dto;

import com.example.formulario_service.model.Formulario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Schema(description = "Respuesta del formulario registrado")
public class FormularioResponse {

    @Schema(description = "ID del formulario", example = "507f1f77bcf86cd799439011")
    private String id;

    @Schema(description = "Nombre del solicitante", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del solicitante", example = "Pérez")
    private String apellido;

    @Schema(description = "Tipo de documento", example = "DNI")
    private String tipoDocumento;

    @Schema(description = "Número de documento", example = "12345678")
    private String numeroDocumento;

    @Schema(description = "Número de celular", example = "+51987654321")
    private String celular;

    @Schema(description = "Correo electrónico", example = "juan.perez@example.com")
    private String correo;

    @Schema(description = "Aceptación de tratamiento de datos", example = "true")
    private Boolean tratamientoDatos;

    @Schema(description = "Campos adicionales dinámicos")
    private Map<String, Object> camposAdicionales;

    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de actualización")
    private LocalDateTime fechaActualizacion;

    public static FormularioResponse fromEntity(Formulario formulario) {
        FormularioResponse response = new FormularioResponse();
        response.setId(formulario.getId());
        response.setNombre(formulario.getNombre());
        response.setApellido(formulario.getApellido());
        response.setTipoDocumento(formulario.getTipoDocumento());
        response.setNumeroDocumento(formulario.getNumeroDocumento());
        response.setCelular(formulario.getCelular());
        response.setCorreo(formulario.getCorreo());
        response.setTratamientoDatos(formulario.getTratamientoDatos());
        response.setCamposAdicionales(formulario.getCamposAdicionales());
        response.setFechaCreacion(formulario.getFechaCreacion());
        response.setFechaActualizacion(formulario.getFechaActualizacion());
        return response;
    }
}
