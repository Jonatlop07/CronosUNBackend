package com.javalimos.CronosUN.excepcion;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UsuarioNoEncontradoRespuesta {
    
    private final String mensaje;
    private final HttpStatus httpStatus;
    private final LocalDateTime tiempoDeOcurrencia;
}
