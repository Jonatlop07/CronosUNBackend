package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.dto.EntradaDiarioDTO;
import com.javalimos.CronosUN.dto.ParametrosFiltroEntradasDeDiario;
import com.javalimos.CronosUN.dto.DatosFiltroEntradasDiario;
import com.javalimos.CronosUN.dto.RespuestaFiltroEntradas;
import com.javalimos.CronosUN.especificacion.EspecificacionDiario;
import com.javalimos.CronosUN.excepcion.RecursoNoEncontradoException;
import com.javalimos.CronosUN.excepcion.UsuarioNoAutorizadoException;
import com.javalimos.CronosUN.excepcion.UsuarioNoEncontradoException;
import com.javalimos.CronosUN.mapeador.MapeadorEntradaDiario;
import com.javalimos.CronosUN.modelo.EntradaDiario;
import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.EntradaDiarioRepository;
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

@Service
@AllArgsConstructor
public class DiarioServicio {
    
    private final MapeadorEntradaDiario mapeadorEntradaDiario;
    private final UsuarioRepository usuarioRepository;
    private final EntradaDiarioRepository entradaDiarioRepository;
    
    @Transactional
    public void registrarEntradaDiario( final Integer idUsuario, final EntradaDiarioDTO nuevaEntradaDiario ) {
        usuarioRepository.findById( idUsuario ).ifPresentOrElse( ( usuario ) -> {
            EntradaDiario entradaDiario = mapeadorEntradaDiario.toEntradaDiario( nuevaEntradaDiario );
            entradaDiario.setUsuario( usuario );
            entradaDiarioRepository.save( entradaDiario );
        }, () -> {
            throw new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO );
        } );
    }
    
    public RespuestaFiltroEntradas obtenerEntradasDiario( final Integer idUsuario, final DatosFiltroEntradasDiario datosFiltro ) {
        Usuario usuario = usuarioRepository.findById( idUsuario )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        Pageable paginacion = PageRequest.of( datosFiltro.getNumeroPagina(), datosFiltro.getTamanioPagina() );
        
        ParametrosFiltroEntradasDeDiario parametrosFiltro = new ParametrosFiltroEntradasDeDiario( datosFiltro, usuario, paginacion );
        
        Page<EntradaDiario> resultadoEntradas = filtrarEntradasDiario( parametrosFiltro );
        Integer numeroDePaginas = resultadoEntradas.getTotalPages();
        
        List<EntradaDiarioDTO> resultadoEntradasDTO = obtenerResultadoFiltroEntradasDiario( resultadoEntradas );
        RespuestaFiltroEntradas respuestaFiltroEntradas = new RespuestaFiltroEntradas( numeroDePaginas, resultadoEntradasDTO );
        
        return respuestaFiltroEntradas;
    }
    
    private Page<EntradaDiario> filtrarEntradasDiario( ParametrosFiltroEntradasDeDiario parametrosFiltro ) {
        DatosFiltroEntradasDiario datosFiltro = parametrosFiltro.getDatosFiltro();
        Usuario usuario = parametrosFiltro.getUsuario();
        Pageable paginacion = parametrosFiltro.getPaginacion();
        EspecificacionDiario especificacionDiario = new EspecificacionDiario( datosFiltro, usuario );
        return entradaDiarioRepository.findAll( especificacionDiario, paginacion );
    }
    
    private List<EntradaDiarioDTO> obtenerResultadoFiltroEntradasDiario( Page<EntradaDiario> resultadoEntradas ) {
        if ( resultadoEntradas.hasContent() ) {
            return resultadoEntradas
                    .getContent()
                    .stream()
                    .map( mapeadorEntradaDiario::toEntradaDiarioDTO )
                    .collect( Collectors.toList() );
        }
        return new ArrayList<EntradaDiarioDTO>();
    }
    
    @Transactional
    public void eliminarEntradaDiario( Integer idUsuario, Integer idEntrada ) {
        if ( !entradaDiarioRepository.existsById( idEntrada ) ) {
            throw new RecursoNoEncontradoException( ENTRADA_DIARIO_NO_ENCONTRADA );
            
        }
        Usuario usuario = usuarioRepository.findById( idUsuario )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        entradaDiarioRepository.findByIdAndUsuario( idEntrada, usuario )
                .ifPresentOrElse(
                        ( entradaDiario ) -> entradaDiarioRepository.deleteById( idEntrada ),
                        () -> {
                            throw new UsuarioNoAutorizadoException( USUARIO_NO_AUTORIZADO );
                        } );
    }
}