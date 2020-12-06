package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.constante.RutasApi;
import com.javalimos.CronosUN.dto.FiltroProyectosPortafolioDTO;
import com.javalimos.CronosUN.dto.ProyectoDTO;
import com.javalimos.CronosUN.servicio.PortafolioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PortafolioControlador {
    
    private PortafolioServicio portafolioServicio;
    
    public PortafolioControlador( PortafolioServicio portafolioServicio ) {
        this.portafolioServicio = portafolioServicio;
    }
    
    @GetMapping( RutasApi.PROYECTO )
    public ResponseEntity<ProyectoDTO> consultarProyectoPortafolio( @PathVariable( "id" ) Integer idProyecto ) {
        ProyectoDTO proyectoConsultado = portafolioServicio.consultarProyectoPortafolio( idProyecto );
        return ResponseEntity.ok( proyectoConsultado );
    }
    
    @GetMapping( RutasApi.CATEGORIAS_PORTAFOLIO )
    public ResponseEntity<List<String>> consultarCategoriasPortafolio( @PathVariable( "id" ) Integer idUsuario ) {
        List<String> categoriasPortafolio = portafolioServicio.consultarCategoriasPortafolio( idUsuario );
        return ResponseEntity.ok( categoriasPortafolio );
    }
    
    @GetMapping( RutasApi.NUM_PAGINAS_PORTAFOLIO )
    public ResponseEntity<Integer> consultarNumeroPaginasPortafolio( @Valid @RequestParam Integer idUsuario ) {
        Integer numeroPaginas = portafolioServicio.consultarNumeroPaginasPortafolio( idUsuario );
        return ResponseEntity.ok( numeroPaginas );
    }
    
    @GetMapping( RutasApi.CONSULTA_PORTAFOLIO_USUARIO )
    public ResponseEntity<List<ProyectoDTO>> consultarPortafolioUsuario( @RequestParam String correo, @RequestParam Integer numeroPagina ) {
        List<ProyectoDTO> proyectosPortafolio = portafolioServicio.consultarPortafolioUsuario( correo, numeroPagina );
        return ResponseEntity.ok( proyectosPortafolio );
    }
    
    @PostMapping( RutasApi.REGISTRO_PROYECTO )
    public ResponseEntity<ProyectoDTO> registrarProyectoPortafolio( @Valid @RequestBody ProyectoDTO nuevoProyecto ) {
        ProyectoDTO proyectoGuardado = portafolioServicio.registrarProyectoPortafolio( nuevoProyecto );
        return ResponseEntity.ok( proyectoGuardado );
    }
    
    @PostMapping( RutasApi.FILTRO_PROYECTOS )
    public ResponseEntity<List<ProyectoDTO>> obtenerProyectosPortafolio( @Valid @RequestBody FiltroProyectosPortafolioDTO datosFiltro ) {
        List<ProyectoDTO> proyectos = portafolioServicio.obtenerProyectosPortafolio( datosFiltro );
        return ResponseEntity.ok( proyectos );
    }
    
    @PutMapping( RutasApi.ACTUALIZACION_PROYECTO )
    public ResponseEntity<ProyectoDTO> actualizarProyectoPortafolio( @RequestBody ProyectoDTO proyectoEditado ) {
        ProyectoDTO proyectoActualizado = portafolioServicio.actualizarProyectoPortafolio( proyectoEditado );
        return ResponseEntity.ok( proyectoActualizado );
    }
    
    @DeleteMapping( RutasApi.PROYECTO )
    public ResponseEntity<?> eliminarProyectoPortafolio( @PathVariable( "id" ) Integer idProyecto ) {
        if ( portafolioServicio.eliminarProyectoPortafolio( idProyecto ) ) {
            return new ResponseEntity<>( HttpStatus.OK );
        }
        return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE );
    }
}
