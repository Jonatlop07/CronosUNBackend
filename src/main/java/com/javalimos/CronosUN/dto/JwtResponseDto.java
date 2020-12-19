package com.javalimos.CronosUN.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDto implements Serializable {
    
    private static final long serialVersionUID = -8091879091924046844L;
    
    private final String jwttoken;

    private final UsuarioDTO usuario;

}
