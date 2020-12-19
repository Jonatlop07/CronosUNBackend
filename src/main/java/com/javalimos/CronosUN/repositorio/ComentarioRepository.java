package com.javalimos.CronosUN.repositorio;

import com.javalimos.CronosUN.modelo.Comentario;
import com.javalimos.CronosUN.modelo.Proyecto;
import com.javalimos.CronosUN.modelo.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {
    Optional<List<Comentario>> findByIdAndUsuario( Integer idComentario, Usuario usuario );
    Optional<List<Comentario>> findByProyecto( Proyecto proyecto );
}