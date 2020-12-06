package com.javalimos.CronosUN.controlador;

import com.javalimos.CronosUN.configuracion.seguridad.JwtTokenUtil;
import com.javalimos.CronosUN.constante.RutasApi;
import com.javalimos.CronosUN.dto.JwtRequestDto;
import com.javalimos.CronosUN.dto.JwtResponseDto;
import com.javalimos.CronosUN.servicio.JwtUserDetailsService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin (origins = "http://localhost:3000")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;
    
    @RequestMapping(value = "/autenticacion", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequestDto authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getAlias(), authenticationRequest.getClave());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getAlias());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Integer idUsuario = userDetailsService.cargarIdUsuario(authenticationRequest.getAlias());
        JwtResponseDto credenciales = new JwtResponseDto(token, idUsuario);

        return ResponseEntity.ok(credenciales);
    }

    private void authenticate(String alias, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(alias, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
