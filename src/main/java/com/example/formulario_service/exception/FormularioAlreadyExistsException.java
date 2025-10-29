package com.example.formulario_service.exception;

public class FormularioAlreadyExistsException extends RuntimeException {

    public FormularioAlreadyExistsException(String message) {
        super(message);
    }
}
