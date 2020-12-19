package com.javalimos.CronosUN.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private final Integer id;
    private final String alias;
    private final String correo;
}
