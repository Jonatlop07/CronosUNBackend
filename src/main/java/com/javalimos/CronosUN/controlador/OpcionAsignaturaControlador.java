package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.constante.MensajesDeRespuesta;
import com.javalimos.CronosUN.dto.AsignaturaDTO;
import com.javalimos.CronosUN.dto.OpcionAsignaturaDTO;
import com.javalimos.CronosUN.servicio.OpcionAsignaturaServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping( "/api/v2/usuario/{id}/opcionesAsignatura" )
@AllArgsConstructor
public class OpcionAsignaturaControlador {
    
    private final OpcionAsignaturaServicio servicio;
    
    @PostMapping( "/registro" )
    public ResponseEntity<?> realizarRegistroAsignaturaOpcion(
            @Valid @PathVariable( "id" ) Integer idUsuario,
            @Valid @RequestBody OpcionAsignaturaDTO opcionAsignaturaDTO ) {
        servicio.realizarRegistroAsignaturaOpcion( idUsuario, opcionAsignaturaDTO );
        return new ResponseEntity<>( MensajesDeRespuesta.OPCION_ASIGNATURA_REGISTRADA, HttpStatus.CREATED );
    }
    
    @GetMapping
    public ResponseEntity<List<AsignaturaDTO>> obtenerOpcionesAsignatura(
            @Valid @PathVariable( "id" ) Integer idUsuario ) {
        List<AsignaturaDTO> opcionesAsignatura = servicio.obtenerOpcionesAsignatura( idUsuario );
        return ResponseEntity.ok( opcionesAsignatura );
    }
    
    @DeleteMapping
    public ResponseEntity<?> restablecerAsignaturaOpcion(
            @Valid @PathVariable Integer idUsuario ) {
        Integer respuesta = servicio.restablecerAsignaturaOpcion( idUsuario );
        return ResponseEntity.ok( respuesta );
    }
}

