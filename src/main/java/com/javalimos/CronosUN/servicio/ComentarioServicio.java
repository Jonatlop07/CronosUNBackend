package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.dto.ComentarioDTO;
import com.javalimos.CronosUN.excepcion.RecursoNoEncontradoException;
import com.javalimos.CronosUN.excepcion.UsuarioNoAutorizadoException;
import com.javalimos.CronosUN.excepcion.UsuarioNoEncontradoException;
import com.javalimos.CronosUN.mapeador.MapeadorComentario;
import com.javalimos.CronosUN.modelo.Comentario;
import com.javalimos.CronosUN.modelo.Proyecto;
import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.ComentarioRepository;
import com.javalimos.CronosUN.repositorio.PortafolioRepository;
import com.javalimos.CronosUN.repositorio.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.javalimos.CronosUN.constante.MensajesDeRespuesta.*;

@Service
@AllArgsConstructor
public class ComentarioServicio {
    
    private final MapeadorComentario mapeadorComentario;
    private final UsuarioRepository usuarioRepository;
    private final PortafolioRepository portafolioRepository;
    private final ComentarioRepository comentarioRepository;
    
    public List<ComentarioDTO> obtenerComentariosProyecto( Integer idUsuario, Integer idProyecto ) {
        Proyecto proyecto = portafolioRepository.findById( idProyecto )
                .orElseThrow( () -> new RecursoNoEncontradoException( PROYECTO_NO_ENCONTRADO ) );
        Optional<List<Comentario>> comentariosProyecto = comentarioRepository.findByProyecto( proyecto );
        
        if ( comentariosProyecto.isPresent() ) {
            return comentariosProyecto
                    .get()
                    .stream()
                    .map( ( comentario ) -> {
                        ComentarioDTO comentarioDTO = mapeadorComentario.toComentarioDTO( comentario );
                        comentarioDTO.setNombreUsuario( comentario.getUsuario().getNombre() );
                        return comentarioDTO;
                    } )
                    .collect( Collectors.toList() );
        }
        return new ArrayList<ComentarioDTO>();
    }
    
    @Transactional
    public void registrarComentario( Integer idUsuario, Integer idProyecto, ComentarioDTO nuevoComentarioDTO ) {
        Usuario usuario = usuarioRepository.findById( idUsuario )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        Proyecto proyecto = portafolioRepository.findById( idProyecto )
                .orElseThrow( () -> new RecursoNoEncontradoException( PROYECTO_NO_ENCONTRADO ) );
        
        Comentario nuevoComentario = mapeadorComentario.toComentario( nuevoComentarioDTO );
        nuevoComentario.setUsuario( usuario );
        nuevoComentario.setProyecto( proyecto );
        
        comentarioRepository.save( nuevoComentario );
    }
    
    @Transactional
    public void eliminarComentario( Integer idUsuario, Integer idComentario ) {
        if ( !comentarioRepository.existsById( idComentario ) ) {
            throw new RecursoNoEncontradoException( COMENTARIO_NO_ENCONTRADO );
        }
        
        Usuario usuario = usuarioRepository.findById( idUsuario )
                .orElseThrow( () -> new UsuarioNoEncontradoException( USUARIO_NO_ENCONTRADO ) );
        comentarioRepository.findByIdAndUsuario( idUsuario, usuario )
                .ifPresentOrElse(
                        ( comentario ) -> comentarioRepository.deleteById( idComentario ),
                        () -> {
                            throw new UsuarioNoAutorizadoException( USUARIO_NO_AUTORIZADO );
                        } );
    }
}
