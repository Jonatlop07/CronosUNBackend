package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.dto.*;
import com.javalimos.CronosUN.servicio.PortafolioServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.PROYECTO_ELIMINADO;
import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.PROYECTO_REGISTRADO;
import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.PROYECTO_ACTUALIZADO;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping( "/api/v2/usuario/{id}/portafolio/proyectos" )
@AllArgsConstructor
public class PortafolioControlador {
    
    private final PortafolioServicio portafolioServicio;
    
    @GetMapping( "/{idProyecto}" )
    public ResponseEntity<ProyectoDTO> consultarProyectoPortafolio( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                                    @NotNull @PathVariable( "idProyecto" ) Integer idProyecto ) {
        ProyectoDTO proyectoConsultado = portafolioServicio.consultarProyectoPortafolio( idUsuario, idProyecto );
        return new ResponseEntity<>( proyectoConsultado, OK );
    }
    
    @PostMapping
    public ResponseEntity<RespuestaFiltroProyectos> obtenerProyectosPortafolio( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                                                @RequestBody( required = false ) DatosFiltroProyectos datosFiltro ) {
        RespuestaFiltroProyectos respuestFiltroProyectos = portafolioServicio.obtenerProyectosPortafolio( idUsuario, datosFiltro );
        return ResponseEntity.ok( respuestFiltroProyectos );
    }
    
    @PostMapping( "/registro" )
    public ResponseEntity<?> registrarProyectoPortafolio( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                          @NotNull @RequestBody ProyectoDTO nuevoProyecto ) {
        ProyectoDTO proyectoGuardado = portafolioServicio.registrarProyectoPortafolio( idUsuario, nuevoProyecto );
        return new ResponseEntity<>( PROYECTO_REGISTRADO, CREATED );
    }
    
    @PutMapping
    public ResponseEntity<?> actualizarProyectoPortafolio( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                           @NotNull @RequestBody ProyectoDTO proyectoEditado ) {
        portafolioServicio.actualizarProyectoPortafolio( idUsuario, proyectoEditado );
        return new ResponseEntity<>( PROYECTO_ACTUALIZADO, NO_CONTENT );
    }
    
    @DeleteMapping( "/{idProyecto}" )
    public ResponseEntity<?> eliminarProyectoPortafolio( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                         @NotNull @PathVariable( "idProyecto" ) Integer idProyecto ) {
        portafolioServicio.eliminarProyectoPortafolio( idUsuario, idProyecto );
        return new ResponseEntity<>( PROYECTO_ELIMINADO, NO_CONTENT );
    }
    
    @GetMapping( "/categorias" )
    public ResponseEntity<List<String>> consultarCategoriasPortafolio( @NotNull @PathVariable( "id" ) Integer idUsuario ) {
        List<String> categoriasPortafolio = portafolioServicio.consultarCategoriasPortafolio( idUsuario );
        return ResponseEntity.ok( categoriasPortafolio );
    }
    
    @PostMapping( "/publicos" )
    public ResponseEntity<RespuestaPortafolioUsuario> consultarPortafolioUsuario( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                                                  @NotNull @RequestBody PeticionPortafolioDTO peticionPortafolioDTO ) {
        RespuestaPortafolioUsuario respuestaPortafolioUsuario = portafolioServicio.consultarPortafolioUsuario( idUsuario, peticionPortafolioDTO );
        return ResponseEntity.ok( respuestaPortafolioUsuario );
    }
}
