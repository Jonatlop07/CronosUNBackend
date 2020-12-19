package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.dto.RegistroUsuarioDTO;
import com.javalimos.CronosUN.mapeador.MapeadorUsuario;
import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InformacionUsuarioServicio {
    
    private final MapeadorUsuario mapeador;
    private final UsuarioRepository repositorio;

    public RegistroUsuarioDTO obtenerInformacionUsuario( Integer id ) {

        Usuario usuario = repositorio.findById(id).get();
        RegistroUsuarioDTO respuestaUsuario = mapeador.toUsuarioDTO( usuario );

        return respuestaUsuario;
    }

    @Transactional
    public RegistroUsuarioDTO modificarUsuario(Integer idUsuario, RegistroUsuarioDTO usuario){

        Usuario usuarioActual = repositorio.findById( idUsuario ).get();

        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setAlias(usuario.getAlias());
        usuarioActual.setCorreo(usuario.getCorreo());
        usuarioActual.setClave(usuario.getClave());
        usuarioActual.setBiografia(usuario.getBiografia());


        RegistroUsuarioDTO usuarioModificado = mapeador.toUsuarioDTO( repositorio.save(usuarioActual) );
        return usuarioModificado;

    }

    @Transactional
    public boolean eliminarUsuario(Integer idUsuario){
        if(repositorio.existsById(idUsuario)){
            repositorio.deleteById(idUsuario);
            return true;
        }
        return false;
    }
}
