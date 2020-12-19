package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.dto.ComentarioDTO;
import com.javalimos.CronosUN.servicio.ComentarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.COMENTARIO_ELIMINADO;
import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.COMENTARIO_REGISTRADO;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping( "/api/v2/usuario/{id}/portafolio/proyectos/{idProyecto}/comentarios" )
@AllArgsConstructor
public class ComentarioControlador {
    
    private final ComentarioServicio comentarioServicio;
    
    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> obtenerComentariosProyecto( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                                           @NotNull @PathVariable( "idProyecto" ) Integer idProyecto ) {
        List<ComentarioDTO> comentariosProyecto = comentarioServicio.obtenerComentariosProyecto( idUsuario, idProyecto );
        return ResponseEntity.ok( comentariosProyecto );
    }
    
    @PostMapping
    public ResponseEntity<?> registrarComentario( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                  @NotNull @PathVariable( "idProyecto" ) Integer idProyecto,
                                                  @NotNull @RequestBody ComentarioDTO nuevoComentarioDTO ) {
        comentarioServicio.registrarComentario( idUsuario, idProyecto, nuevoComentarioDTO );
        return new ResponseEntity<>( COMENTARIO_REGISTRADO, CREATED );
    }
    
    @DeleteMapping( "/{idComentario}" )
    public ResponseEntity<?> eliminarComentario( @NotNull @PathVariable( "id" ) Integer idUsuario,
                                                 @NotNull @PathVariable( "idComentario" ) Integer idComentario ) {
        comentarioServicio.eliminarComentario( idUsuario, idComentario );
        return new ResponseEntity<>( COMENTARIO_ELIMINADO, NO_CONTENT );
    }
}
