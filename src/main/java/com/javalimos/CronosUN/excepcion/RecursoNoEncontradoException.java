package com.javalimos.CronosUN.excepcion;

import javax.persistence.EntityNotFoundException;

public class RecursoNoEncontradoException extends EntityNotFoundException {
    
    public RecursoNoEncontradoException( String mensaje ) {
        super( mensaje );
    }
}
