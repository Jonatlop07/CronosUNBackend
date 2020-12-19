package com.javalimos.CronosUN.excepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class UsuarioNoAutorizadoExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<Object> handleUsuarioNoAutorizadoException( UsuarioNoAutorizadoException e) {
        UsuarioNoAutorizadoRespuesta usuarioNoAutorizadoRespuesta = new UsuarioNoAutorizadoRespuesta(
                e.getMessage(),
                UNAUTHORIZED,
                LocalDateTime.now()
        );
        return new ResponseEntity<>( usuarioNoAutorizadoRespuesta, UNAUTHORIZED );
    }
}
