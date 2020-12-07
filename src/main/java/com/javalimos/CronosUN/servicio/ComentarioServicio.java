package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.dto.ComentarioDTO;
import com.javalimos.CronosUN.mapeador.MapeadorComentario;
import com.javalimos.CronosUN.modelo.Comentario;
import com.javalimos.CronosUN.modelo.Proyecto;
import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.ComentarioRepository;
import com.javalimos.CronosUN.repositorio.PortafolioRepository;
import com.javalimos.CronosUN.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioServicio {
    @Autowired
    private MapeadorComentario mapeadorComentario;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PortafolioRepository portafolioRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<ComentarioDTO> obtenerComentariosProyecto( Integer idProyecto ) {
        Proyecto proyecto = portafolioRepository.findById( idProyecto ).get();
        Optional<List<Comentario>> comentariosProyecto = comentarioRepository.findByProyecto( proyecto );
        if ( !comentariosProyecto.isEmpty() ) {
            return comentariosProyecto.get().stream()
                    .map((comentario) -> {
                        ComentarioDTO comentarioDTO = mapeadorComentario.toComentarioDTO(comentario);
                        comentarioDTO.setNombreUsuario(comentario.getUsuario().getNombre());
                        return comentarioDTO;
                    })
                    .collect(Collectors.toList());
        }
        return new ArrayList<ComentarioDTO>();
    }

    public ComentarioDTO registrarComentario( ComentarioDTO nuevoComentarioDTO ) {
        Usuario usuario = usuarioRepository.findById( nuevoComentarioDTO.getIdUsuario() ).get();
        Proyecto proyecto = portafolioRepository.findById( nuevoComentarioDTO.getIdProyecto() ).get();
        Comentario nuevoComentario = mapeadorComentario.toComentario( nuevoComentarioDTO );
        nuevoComentario.setUsuario( usuario );
        nuevoComentario.setProyecto( proyecto );
        ComentarioDTO comentarioRegistrado = mapeadorComentario
                .toComentarioDTO( comentarioRepository.save( nuevoComentario ) );
        comentarioRegistrado.setNombreUsuario( usuario.getNombre() );
        return comentarioRegistrado;
    }

    public boolean eliminarComentario( Integer idComentario ) {
        if ( comentarioRepository.existsById( idComentario ) ) {
            comentarioRepository.deleteById( idComentario );
            return true;
        }
        return false;
    }
}
