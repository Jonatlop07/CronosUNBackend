package com.javalimos.CronosUN.controlador;


import com.javalimos.CronosUN.dto.EntradaDiarioDTO;
import com.javalimos.CronosUN.dto.DatosFiltroEntradasDiario;
import com.javalimos.CronosUN.dto.RespuestaFiltroEntradas;
import com.javalimos.CronosUN.servicio.DiarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.ENTRADA_ELIMINADA;
import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.ENTRADA_REGISTRADA;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping( "/api/v2/usuario/{id}/portafolio/diario" )
@AllArgsConstructor
public class DiarioControlador {
    
    private final DiarioServicio diarioServicio;
    
    @PostMapping( "/registro" )
    public ResponseEntity<?> registrarEntradaDiario( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                     @NotNull @RequestBody EntradaDiarioDTO nuevaEntradaDiario ) {
        diarioServicio.registrarEntradaDiario( idUsuario, nuevaEntradaDiario );
        return new ResponseEntity<>( ENTRADA_REGISTRADA, OK );
    }
    
    @PostMapping
    public ResponseEntity<RespuestaFiltroEntradas> obtenerEntradasDiario( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                                          @RequestBody( required = false ) DatosFiltroEntradasDiario datosFiltro ) {
        RespuestaFiltroEntradas respuestaFiltroEntradas = diarioServicio.obtenerEntradasDiario( idUsuario, datosFiltro );
        return new ResponseEntity<>( respuestaFiltroEntradas, OK );
    }
    
    @DeleteMapping( "/{idEntrada}" )
    public ResponseEntity<?> eliminarEntradaDiario( @PathVariable( "id" ) Integer idUsuario,
                                                    @NotNull @PathVariable( "idEntrada" ) Integer idEntrada ) {
        diarioServicio.eliminarEntradaDiario( idUsuario, idEntrada );
        return new ResponseEntity<>( ENTRADA_ELIMINADA, NO_CONTENT );
    }
}
