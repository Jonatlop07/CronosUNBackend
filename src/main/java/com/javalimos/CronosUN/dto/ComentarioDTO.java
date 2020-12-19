package com.javalimos.CronosUN.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javalimos.CronosUN.modelo.Proyecto;
import com.javalimos.CronosUN.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {
    
    private Integer id;
    
    private String nombreUsuario;
    
    @NotBlank
    private String contenido;
    
    @NotNull
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    @JsonFormat( pattern = "yyyy-MM-dd" )
    @Temporal( TemporalType.DATE )
    private Date fecha;
    
    @NotNull
    @DateTimeFormat( pattern = "HH:mm:ss" )
    @JsonFormat( pattern = "HH:mm:ss" )
    @Temporal( TemporalType.TIME )
    private Date hora;
    
    @NotNull
    private Integer idProyecto;
}
