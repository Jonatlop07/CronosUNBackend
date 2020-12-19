package com.javalimos.CronosUN.excepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class UsuarioNoEncontradoExceptionHandler {
    
    @ExceptionHandler( value = { UsuarioNoEncontradoException.class } )
    public ResponseEntity<Object> handleUsuarioNoEncontradoException( UsuarioNoEncontradoException e ) {
        UsuarioNoEncontradoRespuesta usuarioNoEncontradoRespuesta = new UsuarioNoEncontradoRespuesta(
                e.getMessage(),
                NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>( usuarioNoEncontradoRespuesta, NOT_FOUND );
    }
}
