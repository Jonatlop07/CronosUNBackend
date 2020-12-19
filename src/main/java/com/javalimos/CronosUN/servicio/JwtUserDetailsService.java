package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.dto.UsuarioDTO;
import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.UsuarioRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository repository;
    
    @Override
    public UserDetails loadUserByUsername( String alias ) throws UsernameNotFoundException {
        Usuario userOption = repository.findByAlias( alias );
        if ( !repository.existsByAlias( alias ) ) {
            throw new UsernameNotFoundException( "Usuario no encontrado con alias: " + alias );
        }
        Usuario usuario = userOption;
        return new org.springframework.security.core.userdetails.User( usuario.getAlias(),
                usuario.getClave(), new ArrayList<>() );
    }
    
    public UsuarioDTO obtenerUsuarioDTO( String alias ) {
        if ( !repository.existsByAlias( alias ) ) {
            throw new UsernameNotFoundException( "Usuario no encontrado con alias: " + alias );
        }
        Usuario usuario = repository.findByAlias( alias );
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getAlias(),
                usuario.getCorreo()
        );
    }
}
