package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.configuracion.seguridad.JwtTokenUtil;
import com.javalimos.CronosUN.dto.JwtRequestDto;
import com.javalimos.CronosUN.dto.JwtResponseDto;
import com.javalimos.CronosUN.dto.RegistroUsuarioDTO;
import com.javalimos.CronosUN.dto.UsuarioDTO;
import com.javalimos.CronosUN.servicio.JwtUserDetailsService;

import javax.validation.Valid;

import com.javalimos.CronosUN.servicio.RegistroServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping( path = "/api/v2/auth" )
public class JwtAuthenticationController {
    
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;
    private RegistroServicio registroServicio;
    
    @PostMapping( path = "/registro" )
    public ResponseEntity<?> realizarRegistroUsuario(
            @Valid @RequestBody RegistroUsuarioDTO registroUsuarioDTO ) {
        registroServicio.realizarRegistroUsuario( registroUsuarioDTO );
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                null,
                registroUsuarioDTO.getAlias(),
                registroUsuarioDTO.getClave()
        );
        return new ResponseEntity<>( usuarioDTO, CREATED );
    }
    
    @PostMapping( value = "/autenticacion" )
    public ResponseEntity<?> createAuthenticationToken( @Valid @RequestBody JwtRequestDto authenticationRequest ) throws Exception {
        authenticate( authenticationRequest.getAlias(), authenticationRequest.getClave() );
        final UserDetails userDetails = userDetailsService.loadUserByUsername( authenticationRequest.getAlias() );
        final String token = jwtTokenUtil.generateToken( userDetails );
        final UsuarioDTO usuarioDTO = userDetailsService.obtenerUsuarioDTO( authenticationRequest.getAlias() );
        JwtResponseDto credenciales = new JwtResponseDto( token, usuarioDTO );
        
        return ResponseEntity.ok( credenciales );
    }
    
    private void authenticate( String alias, String password ) throws Exception {
        try {
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( alias, password ) );
        } catch ( DisabledException e ) {
            throw new Exception( "USER_DISABLED", e );
        } catch ( BadCredentialsException e ) {
            throw new Exception( "INVALID_CREDENTIALS", e );
        }
    }
    
}
