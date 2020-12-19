package com.javalimos.CronosUN.dto;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
public class DatosFiltroEntradasDiario {
    
    private final Integer numeroPagina = 0;
    
    private final Integer tamanioPagina = 10;
    
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private final Date fechaInicio, fechaFin;
    
    @DateTimeFormat( pattern = "HH:mm:ss" )
    @JsonFormat( pattern = "HH:mm:ss" )
    private final Date horaInicio, horaFin;
    
    private final List<Date> fechas;
}
