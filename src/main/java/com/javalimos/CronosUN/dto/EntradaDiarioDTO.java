package com.javalimos.CronosUN.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaDiarioDTO {
    private Integer id;
    
    @NotNull
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    @JsonFormat( pattern = "yyyy-MM-dd" )
    private Date fecha;
    
    @NotNull
    @DateTimeFormat( pattern = "HH:mm:ss" )
    @JsonFormat( pattern = "HH:mm:ss" )
    private Date hora;
    
    @NotBlank
    private String contenido;
}
