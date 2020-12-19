package com.javalimos.CronosUN.excepcion;

import javax.persistence.EntityNotFoundException;

public class UsuarioNoEncontradoException extends EntityNotFoundException {
    public UsuarioNoEncontradoException( String mensaje ) {
        super( mensaje );
    }
}
