package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.constante.RutasApi;
import com.javalimos.CronosUN.dto.RegistroUsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javalimos.CronosUN.servicio.RegistroServicio;

import javax.validation.Valid;

@RestController
@CrossOrigin (origins = "http://localhost:3000")
public class RegistroControlador {
    
    private final RegistroServicio servicio;
    
    public RegistroControlador (RegistroServicio servicio) {
        this.servicio = servicio;        
    }
    
    @PostMapping( RutasApi.CREAR_USUARIO)
    public ResponseEntity<?> realizarRegistroUsuario(
            @Valid @RequestBody RegistroUsuarioDTO usuarioDTO ) {
        Integer idUsuario = servicio.realizarRegistroUsuario( usuarioDTO );
        return ResponseEntity.ok(idUsuario);
    }
}
