package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.dto.*;
import com.javalimos.CronosUN.especificacion.EspecificacionProyecto;
import com.javalimos.CronosUN.excepcion.RecursoNoEncontradoException;
import com.javalimos.CronosUN.excepcion.UsuarioNoAutorizadoException;
import com.javalimos.CronosUN.excepcion.UsuarioNoEncontradoException;
import com.javalimos.CronosUN.mapeador.MapeadorProyecto;
import com.javalimos.CronosUN.modelo.Proyecto;
import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.PortafolioRepository;
import com.javalimos.CronosUN.repositorio.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.*;
import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.USUARIO_NO_ENCONTRADO;

@Service
@AllArgsConstructor
public class PortafolioServicio {
    
    private final MapeadorProyecto mapeadorProyecto;
    private final UsuarioRepository usuarioRepository;
    private final PortafolioRepository portafolioRepository;
    
    public ProyectoDTO consultarProyectoPortafolio( Integer idUsuario, Integer idProyecto ) {
        Usuario usuario = usuarioRepository.findById( idUsuario ).orElseThrow(
                () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        Optional<Proyecto> proyectoConsultado = portafolioRepository.findByIdAndUsuario( idProyecto, usuario );
        if ( proyectoConsultado.isPresent() ) {
            ProyectoDTO proyectoConsultadoDTO = mapeadorProyecto.toProyectoDTO( proyectoConsultado.get() );
            return proyectoConsultadoDTO;
        }
        throw new UsuarioNoAutorizadoException( USUARIO_NO_AUTORIZADO );
    }
    
    public RespuestaFiltroProyectos obtenerProyectosPortafolio( final Integer idUsuario, DatosFiltroProyectos datosFiltro ) {
        Usuario usuario = usuarioRepository.findById( idUsuario )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        Pageable paginacion = PageRequest.of( datosFiltro.getNumeroPagina(), datosFiltro.getTamanioPagina() );
        
        ParametrosFiltroProyectos parametrosFiltro = new ParametrosFiltroProyectos( datosFiltro, usuario, paginacion );
        
        Page<Proyecto> resultadoProyectos = filtrarProyectosPortafolio( parametrosFiltro );
        Integer numeroPaginas = resultadoProyectos.getTotalPages();
        
        List<ProyectoDTO> resultadoProyectosDTO = obtenerResultadoProyectosPortafolio( resultadoProyectos );
        RespuestaFiltroProyectos respuestaFiltroProyectos = new RespuestaFiltroProyectos( numeroPaginas, resultadoProyectosDTO );
        
        return respuestaFiltroProyectos;
    }
    
    private Page<Proyecto> filtrarProyectosPortafolio( ParametrosFiltroProyectos parametrosFiltro ) {
        DatosFiltroProyectos datosFiltro = parametrosFiltro.getDatosFiltro();
        Usuario usuario = parametrosFiltro.getUsuario();
        Pageable paginacion = parametrosFiltro.getPaginacion();
        EspecificacionProyecto especificacionProyecto = new EspecificacionProyecto( datosFiltro, usuario );
        return portafolioRepository.findAll( especificacionProyecto, paginacion );
    }
    
    private List<ProyectoDTO> obtenerResultadoProyectosPortafolio( Page<Proyecto> resultadoProyectos ) {
        if ( resultadoProyectos.hasContent() ) {
            return resultadoProyectos
                    .getContent()
                    .stream()
                    .map( mapeadorProyecto::toProyectoDTO )
                    .collect( Collectors.toList() );
        }
        return new ArrayList<ProyectoDTO>();
    }
    
    @Transactional
    public ProyectoDTO registrarProyectoPortafolio( Integer idUsuario, ProyectoDTO nuevoProyecto ) {
        Usuario usuarioActual = usuarioRepository.findById( idUsuario )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        Proyecto proyecto = mapeadorProyecto.toProyecto( nuevoProyecto );
        proyecto.setUsuario( usuarioActual );
        ProyectoDTO proyectoRegistrado = mapeadorProyecto.toProyectoDTO( portafolioRepository.save( proyecto ) );
        return proyectoRegistrado;
    }
    
    @Transactional
    public void actualizarProyectoPortafolio( Integer idUsuario, ProyectoDTO proyectoEditado ) {
        registrarProyectoPortafolio( idUsuario, proyectoEditado );
    }
    
    @Transactional
    public void eliminarProyectoPortafolio( Integer idUsuario, Integer idProyecto ) {
        if ( !portafolioRepository.existsById( idProyecto ) ) {
            throw new RecursoNoEncontradoException( PROYECTO_NO_ENCONTRADO );
        }
        
        Usuario usuario = usuarioRepository.findById( idUsuario )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        portafolioRepository.findByIdAndUsuario( idUsuario, usuario )
                .ifPresentOrElse(
                        ( proyecto ) -> portafolioRepository.deleteById( idProyecto ),
                        () -> {
                            throw new UsuarioNoAutorizadoException( USUARIO_NO_AUTORIZADO );
                        } );
    }
    
    public List<String> consultarCategoriasPortafolio( Integer idUsuario ) {
        Usuario usuarioActual = usuarioRepository.findById( idUsuario )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        List<ICategoria> categoriasPortafolio = portafolioRepository.findDistinctCategoriasByUsuario( usuarioActual );
        return categoriasPortafolio
                .stream()
                .map( ICategoria::getCategoria )
                .collect( Collectors.toList() );
    }
    
    public RespuestaPortafolioUsuario consultarPortafolioUsuario( Integer idUsuario, PeticionPortafolioDTO peticionPortafolioDTO ) {
        Usuario usuarioActual = usuarioRepository.findByCorreo( peticionPortafolioDTO.getCorreo() )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ));
        Pageable paginacion = PageRequest.of( peticionPortafolioDTO.getNumeroPagina(), peticionPortafolioDTO.getTamanioPagina() );
        
        Page<Proyecto> resultadoPortafolio = portafolioRepository.findByUsuario( usuarioActual, paginacion );
        Integer numeroPaginas = resultadoPortafolio.getTotalPages();
        
        List<ProyectoDTO> resultadoProyectosPortafolioDTO = obtenerResultadoProyectosPortafolio( resultadoPortafolio );
        RespuestaPortafolioUsuario respuestaPortafolioUsuario = new RespuestaPortafolioUsuario( numeroPaginas, resultadoProyectosPortafolioDTO );
        return respuestaPortafolioUsuario;
    }
}
