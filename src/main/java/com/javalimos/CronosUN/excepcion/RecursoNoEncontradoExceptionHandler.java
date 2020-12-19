package com.javalimos.CronosUN.excepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RecursoNoEncontradoExceptionHandler {
    
    @ExceptionHandler( value = { RecursoNoEncontradoException.class } )
    public ResponseEntity<Object> handleRecursoNoEncontradoException( RecursoNoEncontradoException e ) {
        RecursoNoEncontradoRespuesta recursoNoEncontradoRespuesta = new RecursoNoEncontradoRespuesta(
                e.getMessage(),
                NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>( recursoNoEncontradoRespuesta, NOT_FOUND );
    }
}
