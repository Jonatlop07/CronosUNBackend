package com.javalimos.CronosUN.excepcion;

public class UsuarioNoAutorizadoException extends RuntimeException {
    
    public UsuarioNoAutorizadoException( String mensaje ) {
        super(mensaje);
    }
}
