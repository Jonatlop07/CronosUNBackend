package com.javalimos.CronosUN.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PeticionPortafolioDTO {
    @NotNull
    private final String correo;
    
    private Integer numeroPagina = 0;
    
    private Integer tamanioPagina = 10;
}
