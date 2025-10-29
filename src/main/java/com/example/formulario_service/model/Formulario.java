package com.example.formulario_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "formularios")
public class Formulario {

    @Id
    private String id;

    @Indexed
    private String nombre;

    @Indexed
    private String apellido;

    private String tipoDocumento;

    @Indexed(unique = true)
    private String numeroDocumento;

    private String celular;

    @Indexed
    private String correo;

    private Boolean tratamientoDatos;

    private Map<String, Object> camposAdicionales;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    public enum TipoDocumento {
        DNI, CE, PASAPORTE
    }
}
