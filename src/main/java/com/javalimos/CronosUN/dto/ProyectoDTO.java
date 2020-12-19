package com.javalimos.CronosUN.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProyectoDTO {
    
    private Integer id;
    
    @NotBlank
    private String titulo;
    
    private String descripcion;
    
    private String contenido;
    
    @NotNull
    private boolean privacidad;
    
    @NotBlank
    private String estado;
    
    @NotNull
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private Date fechaCreacion;
    
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private Date fechaFinalizacion;
    
    @NotBlank
    private String categoria;
}
