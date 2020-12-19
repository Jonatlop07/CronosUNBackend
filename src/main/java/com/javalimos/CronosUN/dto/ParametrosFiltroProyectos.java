package com.javalimos.CronosUN.dto;

import com.javalimos.CronosUN.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class ParametrosFiltroProyectos {
    
    private final DatosFiltroProyectos datosFiltro;
    private final Usuario usuario;
    private final Pageable paginacion;
}
