package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.dto.FiltroProyectosPortafolioDTO;
import com.javalimos.CronosUN.dto.ICategoria;
import com.javalimos.CronosUN.dto.ProyectoDTO;
import com.javalimos.CronosUN.especificacion.EspecificacionProyecto;
import com.javalimos.CronosUN.mapeador.MapeadorProyecto;
import com.javalimos.CronosUN.modelo.Proyecto;
import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.PortafolioRepository;
import com.javalimos.CronosUN.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortafolioServicio {
    
    @Autowired
    private MapeadorProyecto mapeadorProyecto;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PortafolioRepository portafolioRepository;
    
    private static final int TAMANIO_PAGINA = 10;
    
    public ProyectoDTO consultarProyectoPortafolio( Integer idProyecto ) {
        Proyecto proyectoConsultado = portafolioRepository.findById( idProyecto ).get();
        ProyectoDTO proyectoConsultadoDTO = mapeadorProyecto.toProyectoDTO( proyectoConsultado );
        return proyectoConsultadoDTO;
    }
    
    public List<String> consultarCategoriasPortafolio( Integer idUsuario ) {
        Usuario usuarioActual = usuarioRepository.findById( idUsuario ).get();
        List<ICategoria> categoriasPortafolio = portafolioRepository.findDistinctCategoriasByUsuario( usuarioActual );
        return categoriasPortafolio
                .stream()
                .map( ICategoria::getCategoria )
                .collect( Collectors.toList() );
    }
    
    public Integer consultarNumeroPaginasPortafolio( Integer idUsuario ) {
        Usuario usuarioActual = usuarioRepository.findById( idUsuario ).get();
        Integer numeroProyectos = portafolioRepository.countByUsuario( usuarioActual );
        final Integer numeroPaginas = ( int ) Math.ceil( numeroProyectos / TAMANIO_PAGINA );
        System.out.println( numeroPaginas );
        return numeroPaginas;
    }
    
    public List<ProyectoDTO> consultarPortafolioUsuario( String correo, Integer numeroPagina ) {
        Usuario usuarioActual = usuarioRepository.findByCorreo( correo );
        Pageable paginacion = PageRequest.of( numeroPagina, TAMANIO_PAGINA );
        Page<Proyecto> resultadoPortafolio = portafolioRepository.findByUsuario( usuarioActual, paginacion );
        return obtenerResultadoProyectosPortafolio( resultadoPortafolio );
    }
    
    public ProyectoDTO registrarProyectoPortafolio( ProyectoDTO nuevoProyecto ) {
        Usuario usuarioActual = usuarioRepository.findById( nuevoProyecto.getIdUsuario() ).get();
        Proyecto proyecto = mapeadorProyecto.toProyecto( nuevoProyecto );
        proyecto.setUsuario( usuarioActual );
        ProyectoDTO proyectoRegistrado = mapeadorProyecto.toProyectoDTO( portafolioRepository.save( proyecto ) );
        return proyectoRegistrado;
    }
    
    public List<ProyectoDTO> obtenerProyectosPortafolio( FiltroProyectosPortafolioDTO datosFiltro ) {
        Pageable paginacion = PageRequest.of( datosFiltro.getNumeroPagina(), TAMANIO_PAGINA );
        Page<Proyecto> resultadoProyectos = filtrarProyectosPortafolio( datosFiltro, paginacion );
        return obtenerResultadoProyectosPortafolio( resultadoProyectos );
    }
    
    private Page<Proyecto> filtrarProyectosPortafolio( FiltroProyectosPortafolioDTO datosFiltro, Pageable paginacion ) {
        Usuario usuarioActual = usuarioRepository.findById( datosFiltro.getIdUsuario() ).get();
        EspecificacionProyecto especificacionProyecto = new EspecificacionProyecto( datosFiltro, usuarioActual );
        
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
    
    public ProyectoDTO actualizarProyectoPortafolio( ProyectoDTO proyectoEditado ) {
        proyectoEditado.setId( proyectoEditado.getId() );
        return registrarProyectoPortafolio( proyectoEditado );
    }
    
    public boolean eliminarProyectoPortafolio( Integer idProyecto ) {
        if ( portafolioRepository.existsById( idProyecto ) ) {
            portafolioRepository.deleteById( idProyecto );
            return true;
        }
        return false;
    }
}
