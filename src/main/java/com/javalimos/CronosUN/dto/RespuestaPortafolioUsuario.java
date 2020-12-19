package com.javalimos.CronosUN.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RespuestaPortafolioUsuario {
    private final Integer numeroPaginas;
    private final List<ProyectoDTO> proyectos;
}
