package com.javalimos.CronosUN.servicio;

import com.javalimos.CronosUN.dto.EntradaDiarioDTO;
import com.javalimos.CronosUN.dto.FiltroEntradasDiarioDTO;
import com.javalimos.CronosUN.mapeador.MapeadorEntradaDiario;
import com.javalimos.CronosUN.modelo.EntradaDiario;
import com.javalimos.CronosUN.modelo.Usuario;
import com.javalimos.CronosUN.repositorio.EntradaDiarioRepository;
import com.javalimos.CronosUN.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiarioServicio {
    
    @Autowired
    private MapeadorEntradaDiario mapeadorEntradaDiario;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EntradaDiarioRepository entradaDiarioRepository;
    
    private static final int TAMANIO_PAGINA = 10;
    
    public Integer consultarNumeroPaginasDiario( Integer idUsuario ) {
        Usuario usuarioActual = usuarioRepository.findById( idUsuario ).get();
        Integer numeroEntradas = entradaDiarioRepository.countByUsuario( usuarioActual );
        final Integer numeroPaginas = (int) Math.ceil( numeroEntradas / TAMANIO_PAGINA );
        return numeroPaginas;
    }
    
    public List<EntradaDiarioDTO> obtenerEntradasDiario( FiltroEntradasDiarioDTO datosFiltro ) {
        System.out.println(datosFiltro.getIdUsuario());
        Pageable paginacion = PageRequest.of( datosFiltro.getNumeroPagina(), TAMANIO_PAGINA );
        Page<EntradaDiario> resultadoEntradas = filtrarEntradasDiario( datosFiltro, paginacion );
        return obtenerResultadoFiltroEntradasDiario( resultadoEntradas );
    }
    
    private Page<EntradaDiario> filtrarEntradasDiario( FiltroEntradasDiarioDTO datosFiltro, Pageable paginacion ) {
        Usuario usuarioActual = usuarioRepository.findById( datosFiltro.getIdUsuario() ).get();
        Date fechaInicial = datosFiltro.getFechaInicio(), fechaFinal = datosFiltro.getFechaFin();
        List<Date> fechas = datosFiltro.getFechas();
        
        if ( fechaInicial != null && fechaFinal != null ) {
            return filtrarEntradasDiarioPorRango( datosFiltro, paginacion );
        }
        
        if ( fechas != null && !fechas.isEmpty() ) {
            return entradaDiarioRepository.findByUsuarioAndFechaIn( usuarioActual, fechas, paginacion );
        }
        return entradaDiarioRepository.findAll( paginacion );
    }
    
    private Page<EntradaDiario> filtrarEntradasDiarioPorRango( FiltroEntradasDiarioDTO datosFiltro, Pageable paginacion ) {
        Usuario usuarioActual = usuarioRepository.findById( datosFiltro.getIdUsuario() ).get();
        Date fechaInicial = datosFiltro.getFechaInicio(), fechaFinal = datosFiltro.getFechaFin();
        Date horaInicial = datosFiltro.getHoraInicio(), horaFinal = datosFiltro.getHoraFin();
        
        if ( horaInicial != null && horaFinal != null ) {
            return entradaDiarioRepository
                    .findByUsuarioAndFechaBetweenAndHoraBetween( usuarioActual, fechaInicial, fechaFinal, horaInicial, horaFinal, paginacion );
        }
        return entradaDiarioRepository.findByUsuarioAndFechaBetween( usuarioActual, fechaInicial, fechaFinal, paginacion );
    }
    
    private List<EntradaDiarioDTO> obtenerResultadoFiltroEntradasDiario( Page<EntradaDiario> resultadoEntradas ) {
        if ( resultadoEntradas.hasContent() ) {
            return resultadoEntradas
                    .getContent()
                    .stream()
                    .map( mapeadorEntradaDiario::toEntradaDiarioDTO )
                    .collect( Collectors.toList() );
        }
        return new ArrayList<EntradaDiarioDTO>();
    }
    
    public EntradaDiarioDTO registrarEntradaDiario( EntradaDiarioDTO nuevaEntradaDiario ) {
        Usuario usuarioActual = usuarioRepository.findById( nuevaEntradaDiario.getIdUsuario() ).get();
        EntradaDiario entradaDiario = mapeadorEntradaDiario.toEntradaDiario( nuevaEntradaDiario );
        entradaDiario.setUsuario( usuarioActual );
        EntradaDiarioDTO entradaGuardada = mapeadorEntradaDiario
                .toEntradaDiarioDTO( entradaDiarioRepository.save( entradaDiario ) );
        return entradaGuardada;
    }
    
    public boolean eliminarEntradaDiario( Integer idEntrada ) {
        if ( entradaDiarioRepository.existsById( idEntrada ) ) {
            entradaDiarioRepository.deleteById( idEntrada );
            return true;
        }
        return false;
    }
}