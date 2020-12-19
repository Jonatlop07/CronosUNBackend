package com.javalimos.CronosUN.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosFiltroProyectos {
    
    private int numeroPagina = 0;
    
    private int tamanioPagina = 10;
    
    private String titulo;
    
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private Date fechaInicio, fechaFin;
    
    private Boolean privado;
    
    private String estado;
    
    private List<String> categorias;
}
