package com.sistemaOficina.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.sistemaOficina.backend.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        
        if (cause instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException unrecognizedEx = (UnrecognizedPropertyException) cause;
            String fieldName = unrecognizedEx.getPropertyName();
            String message = String.format("Campo desconhecido '%s' não é permitido na requisição", fieldName);
            return ResponseEntity.badRequest().body(new ErrorResponse(message));
        }
        
        if (cause instanceof JsonMappingException) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Formato JSON inválido na requisição"));
        }
        
        return ResponseEntity.badRequest().body(new ErrorResponse("Erro ao processar a requisição JSON"));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Dados de entrada inválidos"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro interno do servidor"));
    }
}
