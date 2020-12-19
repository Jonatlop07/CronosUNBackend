package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.dto.RegistroUsuarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javalimos.CronosUN.servicio.InformacionUsuarioServicio;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping( "/api/v2/usuario/{id}/cuenta" )
@AllArgsConstructor
public class InformacionUsuarioControlador {
    
    private final InformacionUsuarioServicio servicio;
    
    @GetMapping
    public ResponseEntity<?> obtenerInformacionUsuario(
            @PathVariable( "id" ) Integer idUsuario ) {
        RegistroUsuarioDTO usuarioDTO = servicio.obtenerInformacionUsuario( idUsuario );
        return ResponseEntity.ok( usuarioDTO );
    }
    
    @PutMapping
    public ResponseEntity<?> modificarUsuario(
            @PathVariable( "id" ) Integer idUsuario, @RequestBody RegistroUsuarioDTO usuarioDTO ) {
        RegistroUsuarioDTO usuarioModificado = servicio.modificarUsuario( idUsuario, usuarioDTO );
        return ResponseEntity.ok( usuarioModificado );
    }
    
    @DeleteMapping
    public ResponseEntity<?> eliminarUsuario(
            @PathVariable( "id" ) Integer idUsuario ) {
        if ( servicio.eliminarUsuario( idUsuario ) ) {
            return new ResponseEntity<>( OK );
        }
        return new ResponseEntity<>( NOT_ACCEPTABLE );
    }
    
}
