package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.servicio.RecuperacionClaveServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.CORREO_RECUPERACION_ENVIADO;

@RestController
@AllArgsConstructor
@RequestMapping( path = "/api/v2/usuario/" )
public class RecuperacionClaveControlador {
    private final RecuperacionClaveServicio servicio;
    
    @GetMapping( "/recuperarClave" )
    public ResponseEntity<?> recuperarClave( @Valid @RequestParam String correo ) {
        Integer idUsuario = servicio.realizarRecuperacionClave( correo );
        return new ResponseEntity<>( CORREO_RECUPERACION_ENVIADO, HttpStatus.NO_CONTENT );
    }
}
