
package com.javalimos.CronosUN.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestDto implements Serializable {
    
    private static final long serialVersionUID = 5926468583005150707L;

    @NotNull(message = "El alias es obligatorio.")
    private String alias;
    
    @NotNull(message = "La clave es obligatoria.")
    private String clave;
}
