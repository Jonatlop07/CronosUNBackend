package com.javalimos.CronosUN.dto;

import java.util.List;


import com.javalimos.CronosUN.modelo.AsignaturaOpcion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class OpcionAsignaturaDTO {
    @NotBlank
    private String nombre;

    @NotBlank
    private String creditos;
    
    @NotNull
    private List<OpcionGrupoDTO> grupos;
}
