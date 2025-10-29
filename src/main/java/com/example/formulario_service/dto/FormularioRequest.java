package com.example.formulario_service.dto;

import com.example.formulario_service.model.Formulario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "Solicitud de registro de formulario")
public class FormularioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    @Schema(description = "Nombre del solicitante", example = "Juan")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 1, max = 100, message = "El apellido debe tener entre 1 y 100 caracteres")
    @Schema(description = "Apellido del solicitante", example = "Pérez")
    private String apellido;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Pattern(regexp = "DNI|CE|PASAPORTE", message = "Tipo de documento no válido")
    @Schema(description = "Tipo de documento", example = "DNI")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 1, max = 20, message = "El número de documento debe tener entre 1 y 20 caracteres")
    @Schema(description = "Número de documento", example = "12345678")
    private String numeroDocumento;

    @NotBlank(message = "El celular es obligatorio")
    @Size(min = 1, max = 20, message = "El celular debe tener entre 1 y 20 caracteres")
    @Schema(description = "Número de celular", example = "+51987654321")
    private String celular;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 255, message = "El correo no puede exceder 255 caracteres")
    @Schema(description = "Correo electrónico", example = "juan.perez@example.com")
    private String correo;

    @AssertTrue(message = "Debe aceptar el tratamiento de datos")
    @Schema(description = "Aceptación de tratamiento de datos", example = "true")
    private Boolean tratamientoDatos;

    @Schema(description = "Campos adicionales dinámicos")
    private Map<String, Object> camposAdicionales;

    public Formulario toEntity() {
        Formulario formulario = new Formulario();
        formulario.setNombre(this.nombre);
        formulario.setApellido(this.apellido);
        formulario.setTipoDocumento(this.tipoDocumento);
        formulario.setNumeroDocumento(this.numeroDocumento);
        formulario.setCelular(this.celular);
        formulario.setCorreo(this.correo);
        formulario.setTratamientoDatos(this.tratamientoDatos);
        formulario.setCamposAdicionales(this.camposAdicionales);
        return formulario;
    }
}
